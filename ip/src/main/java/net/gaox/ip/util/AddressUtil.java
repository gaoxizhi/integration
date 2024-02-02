package net.gaox.ip.util;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * <p> 获取IP城市信息工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 23:15
 */
public class AddressUtil {

    /**
     * 根据IP地址查询IP城市信息
     *
     * @param ip IP地址
     * @return IP城市信息
     */
    public static String getCityInfo(String ip) {
        try {
            String dbPath = "ip2region/ip2region.xdb";
            File file = ResourceUtils.getFile("classpath:" + dbPath);
            // Searcher.newWithFileOnly("ip2region/ip2region.xdb");
            Searcher searcher = Searcher.newWithFileOnly(file.getAbsolutePath());
            // 开始查询
            return searcher.searchByStr(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认返回空字符串
        return "";
    }

}
