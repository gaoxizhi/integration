package net.gaox.ip.util;

import com.alibaba.fastjson.JSONObject;
import net.gaox.ip.entry.Addresses;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/29 18:21
 */
public class IpUtil {

    /**
     * 未知
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 获取指定ip的地理位置信息
     *
     * @param content 请求的参数 格式为：name=xxx&pwd=xxx
     * @return Addr对象
     */
    public static Addresses getAddresses(String content) {
        return getAddresses(content, "utf-8");
    }

    /**
     * 获取指定ip的地理位置信息
     * https://whois.pconline.com.cn/ipJson.jsp?json=true&ip=239.197.206.19
     *
     * @param content  请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return Addr对象
     */
    public static Addresses getAddresses(String content, String encoding) {

        // 这里调用淘宝API，取得IP所在的省市区信息
        String urlStr = "https://ip.taobao.com/outGetIpInfo";
        String returnStr = getResult(urlStr, "ip=" + content, encoding);
        if (null != returnStr) {
            return JSONObject.parseObject(decodeUnicode(returnStr)).getObject("data", Addresses.class);
        }
        return null;
    }

    /**
     * 获取指定ip的地理位置信息
     *
     * @param ip ip地址
     * @return Addr对象
     */
    public static Addresses getAddressesByPconline(String ip) {

        // 这里调用淘宝API，取得IP所在的省市区信息
        String urlStr = "https://whois.pconline.com.cn/ipJson.jsp";
        String returnStr = getResult(urlStr, "json=true&ip=" + ip, "gbk");
        if (null != returnStr) {
            JSONObject json = JSONObject.parseObject(decodeUnicode(returnStr));
            Addresses addresses = new Addresses().setIp(ip)
                    .setRegion(json.getString("pro"))
                    .setRegionId(json.getString("proCode"))
                    .setCity(json.getString("city"))
                    .setCityId(json.getString("cityCode"))
                    .setIsp(json.getString("addr"));
            return addresses;
        }
        return null;
    }

    /**
     * @param urlStr   请求的地址
     * @param content  请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return 请求结果
     */
    private static String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(2000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(2000);
            // 是否打开输出流 true|false
            connection.setDoOutput(true);
            // 是否打开输入流true|false
            connection.setDoInput(true);
            // 提交方法POST|GET
            connection.setRequestMethod("POST");
            // 是否缓存true|false
            connection.setUseCaches(false);
            // 打开连接端口
            connection.connect();
            // 打开输出流往对端服务器写数据
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.writeBytes(content);
            // 刷新
            out.flush();
            // 关闭输出流
            out.close();
            // 往对端写完数据对端服务器返回数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));
            // ,以BufferedReader流来读取  
            StringBuilder builder = new StringBuilder();
            String line = "";
            while (null != (line = reader.readLine())) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                // 关闭连接
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * unicode 转换成 中文
     *
     * @param theString unicode码
     * @return 中文信息
     */
    private static String decodeUnicode(String theString) {
        char one;
        int len = theString.length();
        StringBuilder buffer = new StringBuilder(len);
        for (int i = 0; i < len; ) {
            one = theString.charAt(i++);
            if (one == '\\') {
                one = theString.charAt(i++);
                if (one == 'u') {
                    int value = 0;
                    for (int j = 0; j < 4; j++) {
                        one = theString.charAt(j++);

                        if (one >= '0' && one <= '9') {
                            value = (value << 4) + one - '0';
                        } else if (one >= 'a' && one <= 'z') {
                            value = (value << 4) + 10 + one - 'a';
                        } else if (one >= 'A' && one <= 'Z') {
                            value = (value << 4) + 10 + one - 'A';
                        } else {
                            throw new IllegalArgumentException("Malformed encoding.");
                        }
                    }
                    buffer.append((char) value);
                } else {
                    if (one == 't') {
                        one = '\t';
                    } else if (one == 'r') {
                        one = '\r';
                    } else if (one == 'n') {
                        one = '\n';
                    } else if (one == 'f') {
                        one = '\f';
                    }
                    buffer.append(one);
                }
            } else {
                buffer.append(one);
            }
        }
        return buffer.toString();
    }


    /**
     * 获取 IP地址
     * 使用 Nginx等反向代理软件， 则不能通过 request.getRemoteAddr()获取 IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * X-Forwarded-For中第一个非 unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "0:0:0:0:0:0:0:1";
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println("Header Name: " + headerName + ", Header Value: " + headerValue);
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip.contains(",") ? ip.split(",")[0] : ip;
    }

    public static Addresses getAddressesInfo(HttpServletRequest request) {
        return getAddresses(getIpAddress(request));
    }

    public static void main(String[] args) {
        final long l = System.currentTimeMillis();
        String ip = "115.220.200.21";
        Addresses address = getAddressesByPconline(ip);
        System.out.println(String.format("耗时：%sms", System.currentTimeMillis() - l));
        System.out.println(address.getCountry());
        System.out.println(address.getRegion());
        System.out.println(address.getCity());
        System.out.println(address.getIsp());
    }
}