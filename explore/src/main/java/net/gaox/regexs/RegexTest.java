package net.gaox.regexs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> 正则表达式应用 </p>
 *
 * @author gaox·Eric
 * @date 2020/10/1 12:19
 */
public class RegexTest {

    public static void main(String[] args) {
        // 匹配
        matcher("asfsdf23323", "\\w+");
        // 匹配组
        group();
        // replaceAll 替换
        replace();
        // appendReplacement 替换
        replace2();
        // 分割
        split();
        // 爬取网页上超链接
        webSpider();
    }


    /**
     * 匹配字符串（完全匹配）
     *
     * @param input 字符串
     * @param regex 正则表达式
     */
    public static void matcher(String input, String regex) {
        //表达式对象
        Pattern p = Pattern.compile(regex);
        //创建Matcher对象

        Matcher m = p.matcher(input);
        // 尝试将整个字符序列与该模式匹配
        boolean matches = m.matches();
        System.out.println(String.format("字符串=[%s]与正则表达式=[%s]匹配结果：[%s]", input, regex, matches));
    }

    /**
     * 按照正则分组匹配，分组匹配括号表达式()
     */
    public static void group() {
        //表达式对象
        String regex = "([a-z]+)([0-9]+)";
        Pattern p = Pattern.compile(regex);
        //创建Matcher对象
        String input = "aa232**ssd445*sds223";
        Matcher m = p.matcher(input);

        int i = 1;
        // find 方法扫描输入的序列，查找与该模式匹配的下一个子序列
        while (m.find()) {
            // group()，group(0)匹配整个表达式的子字符串
            String group = m.group();
            // 第一个()内表达式匹配字符串"[a-z]+"
            String group1 = m.group(1);
            String group2 = m.group(2);
            System.out.println(String.format(
                    "字符串=[%s]与正则表达式=[%s]匹配分组%d，符合表达式整个字符串=[%s]，分组1=[%s]，分组2=[%s]",
                    input, regex, i++, group, group1, group2));
        }
    }

    /**
     * 按照正则替换字符串 replaceAll
     */
    public static void replace() {
        //表达式对象
        String regex = "[0-9]";
        Pattern p = Pattern.compile(regex);
        //创建Matcher对象
        String input = "aa232**ssd445*sds223";
        Matcher m = p.matcher(input);
        //替换
        String newStr = m.replaceAll("#");
        System.out.println(String.format("字符串=[%s]将符合正则表达式=[%s]的字符串替换为[#]，结果：[%s]", input, regex, newStr));
    }

    /**
     * 按照正则替换字符串2 appendReplacement
     */
    public static void replace2() {
        //表达式对象
        String regex = "[0-9]";
        Pattern p = Pattern.compile(regex);
        //创建Matcher对象
        String input = "aa232**ssd445*sds223";
        Matcher m = p.matcher(input);

        //替换
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(buffer, "#");
        }
        m.appendTail(buffer);
        String str = buffer.toString();

        System.out.println(String.format("字符串=[%s]将符合正则表达式=[%s]的字符串替换为[#]，结果：[%s]", input, regex, str));
    }

    /**
     * 按照正则切割字符串
     */
    public static void split() {
        String input = "a232b4334c3434";
        String regex = "\\d+";
        String[] strings = input.split(regex);

        System.out.println(String.format("字符串=[%s]按照正则表达式=[%s]切分，共[%d]组，内容：(%s)",
                input, regex, strings.length, Arrays.toString(strings)));

    }

    public static void webSpider() {

        String destStr = getURLContent("http://www.163.com", "gbk");

        // Pattern p = Pattern.compile("<a[\\s\\S]+?</a>");
        // 取到的超链接的整个内容
        String regexStr = "href=\"([\\w\\s./:]+?)\"";
        List<String> result = getMatherSubsets(destStr, regexStr);
        for (String temp : result) {
            System.out.println(temp);
        }
    }

    /**
     * 获得urlStr对应的网页的源码内容
     *
     * @param urlStr 网页地址
     * @return 网页html源码
     */
    public static String getURLContent(String urlStr, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName(charset)));
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 从网页源码中获取所有超链接地址文本
     *
     * @param destStr  网页源码
     * @param regexStr 正则表达式字符串
     * @return 所有超链接地址文本
     */
    public static List<String> getMatherSubsets(String destStr, String regexStr) {
        Pattern p = Pattern.compile(regexStr);
        Matcher m = p.matcher(destStr);
        List<String> result = new ArrayList<>();
        while (m.find()) {
            result.add(m.group(1));
        }
        return result;
    }
}
