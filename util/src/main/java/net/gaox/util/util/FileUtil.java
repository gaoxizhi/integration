package net.gaox.util.util;

import java.io.File;
import java.io.IOException;

/**
 FileUtil
 * @author gaox·Eric
 * @date 2019/4/20 14:46
 */
public class FileUtil {
    /**
     * 创建一个文件
     *
     * @param pathFileName
     * @return
     */
    public static File createFile(File pathFileName) {
        File f = pathFileName;
        //判断父目录路径是否存在，即test.txt前的I:\a\b\
        if (!f.getParentFile().exists()) {
            try {
                //不存在则创建父目录
                f.getParentFile().mkdirs();
                f.createNewFile();
                return f;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
    /**
     * 获得项目路径
     *
     * @return
     */
    public static String getProjectPath0() {
        //参数为空
        File directory = new File("");
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseFile;
        /**
         * 或者使用
         * return System.getProperty("user.dir");
         */
    }
    /**
     * 获得文件名后缀
     *
     * @param name
     * @return
     */
    public static String getSuffix(String name) {
        return name.substring(name.lastIndexOf('.') + 1);
    }
    /**
     * 获得文件名
     *
     * @param name
     * @return
     */
    public static String getName(String name) {
        File tempFile = new File(name.trim());
        String tempName = tempFile.getName();
        return tempName.replaceAll(tempName.substring(tempName.lastIndexOf(".")), "");
    }
    /**
     * 获得文件路径
     *
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {

        //win环境下 ？
        if (!isAbsolutePath(path)) {
            //这种方式获取的路径，其中的空格会被使用“%20”代替
            String tempPath = FileUtil.class.getClassLoader().getResource(path).getPath();
            //将“%20”替换回空格
            return tempPath.replaceAll("%20", " ");
        }
        return path;
    }
    /**
     * 判断是否是绝对路径
     *
     * @param path
     * @return
     */
    private static boolean isAbsolutePath(String path) {
        //斜线
        String OBLIQUE_NOUN = "/";
        //反斜线backslash
        String BACKSLASH = "\\";
        //冒号colon
        String COLON = ":";
        //波浪线break line
        String BREAK_LINE = "~";
        if (path.startsWith(OBLIQUE_NOUN)) {
            return true;
        }
        // windows
        if (isWinOS()) {
            if (path.contains(COLON) || path.startsWith(BACKSLASH)) {
                return true;
            }
            // Windows中没有的，Unix特有的路径符号
            // not windows, just unix compatible
        } else {
            if (path.startsWith(BREAK_LINE)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否windows系统
     */
    private static boolean isWinOS() {
        boolean isWinOS = false;
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String sharpOsName = osName.replaceAll("windows", "{windows}")
                    .replaceAll("^win([^a-z])", "{windows}$1").replaceAll("([^a-z])win([^a-z])", "$1{windows}$2");
            isWinOS = sharpOsName.contains("{windows}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWinOS;
    }
}