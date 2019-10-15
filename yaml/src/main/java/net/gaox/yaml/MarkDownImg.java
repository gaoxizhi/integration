package net.gaox.yaml;

import java.io.*;

/**
 * <p> 使用一些MarkDown软件写博客时大都会设置图片自动上传，这样只需要复制一遍MarkDown文本即可粘贴到多个平台发布，
 * 很多免费的图床插件都是将图片上传至微博图床，毕竟免费。但微博并不会那么大方，
 * 在请求微博图片时会检测request头部Referer字段的值，来鉴别请求来源。
 * <p>
 * 解决方法就是将Referer禁掉，例如可以在html文件中设置meta标签如下：
 * Copymeta name=referrer content=never
 * <p>
 * 另一种方法是在每个img的标签设置referrerpolicy属性：
 * Copyimg src= referrerpolicy=no-referrer
 * <p>
 * 如果你的图片引用比较少还好，如果引用的图片多了，一个一个改就太麻烦，交给程序去帮我们做这件事就非常合适。
 * 设计一个jar包，只需要执行一遍的java -jar，即可全自动扫描jar包所在目录下的所有md文件，
 * 并自动将 ![]() 更换为 img 标签的形式，且支持以追加参数的形式指定扫描目录。 </p>
 *
 * @author gaox·Eric
 * @date 2019/9/5 12:34
 * 使用方法： java -jar 的形式执行jar包
 * 默认扫描jar包所在路径，不会递归扫描
 * 可以追加参数 path=xxx 指定扫描xxx目录
 */


public class MarkDownImg {

    public static void main(String[] args) throws IOException {
        String path = null;
        for (String arg : args) {
            if (arg.startsWith("path=")) {
                path = arg.substring(5);
                break;
            }
        }
        if (path == null) {
            path = System.getProperty("user.dir");
        }
        System.out.println("--开始扫描目录：" + path);
        File file = new File(path);
        File[] list = file.listFiles();
        if (list == null) {
            System.out.println("--空目录");
            return;
        }
        //创建输出目录
        String out = path + "/out/";
        if (!new File(out).exists()) {
            new File(out).mkdir();
        }
        for (File value : list) {
            String name = value.getName();
            boolean md = value.isFile() && (name.endsWith(".md") || name.endsWith(".MD"));
            if (!md) {
                continue;
            }
            System.out.println("--" + value.getName());
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(value)));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(out + name))))
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String src = src(line);
                    writer.write(src + "\n");
                }
                writer.flush();
            }
        }
        System.out.println("--结束");
    }

    /**
     * 提取img的src
     */
    private static String src(String s) {
        String[] split = s.split("!");
        for (String value : split) {
            if (value.length() > 4 && value.contains("[") && value.contains("]") && value.contains("(") && value.contains(")")) {
                int start = value.lastIndexOf("(");
                int end = value.lastIndexOf(")");
                String s1 = value.substring(start + 1, end);
                s = s.replace("!" + value, "<img src=\"" + s1 + "\" referrerPolicy=\"no-referrer\"/>");
            }
        }
        return s;
    }
}