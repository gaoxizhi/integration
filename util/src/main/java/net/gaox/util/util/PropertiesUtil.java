package net.gaox.util.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * <p> Properties 工具类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-27 00:39
 */

@Slf4j
public final class PropertiesUtil {

    /**
     * 扩展名为 properties 的文件
     */
    private static final String REGEX_PROPERTIES = "^(.*).properties$";

    /**
     * 查找文件的匹配正则
     */
    private static final Pattern PATTERN_PROPERTIES = Pattern.compile(REGEX_PROPERTIES);

    private static File propertiesFile = new File("./");
    private static Properties properties = new Properties();

    static {
        init();
    }

    /**
     * 默认无参构造器 不对外提供
     */
    private PropertiesUtil() {
    }

    /**
     * 获取配置
     *
     * @return 配置
     */
    public static Properties get() {
        return getProperties();
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return value
     */
    public static String get(final String key) {
        return getProperties().getProperty(key);
    }

    /**
     * 获取配置文件，为空时，返回默认值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value 或 默认值
     */
    public static String get(final String key, final String defaultValue) {
        String value = getProperties().getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    private static Properties getProperties() {
        return properties;
    }

    /**
     * 初始化配置文件
     */
    private static void init() {
        // 获取classpath
        String classpath = ClassUtil.getClasspath();
        if (classpath != null) {
            // 从classpath的父节点开始查找
            File baseDir = new File(classpath).getParentFile();
            loadDir(baseDir);
        }
    }

    /**
     * 递归查找配置文件<br/>
     *
     * @param baseDir 查找的文件夹
     */
    private static void loadDir(File baseDir) {
        // 判断目录是否存在
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return;
        }
        String tempPath;
        File[] files = baseDir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                tempPath = file.getAbsolutePath();
                if (PATTERN_PROPERTIES.matcher(tempPath).matches()) {
                    // 匹配成功，将文件路径添加到结果集
                    propertiesFile = file;
                    load(file);
                }
            } else if (file.isDirectory()) {
                loadDir(file);
            }
        }
    }

    /**
     * 读取配置文件
     *
     * @param file
     */
    private static void load(File file) {
        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            getProperties().load(isr);
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件" + file.getName() + "出现异常", e);
        }
    }

    /**
     * 通过配置文件路径和名称，热加载Properties
     */
    public static void load(String propsName) {
        try (InputStream is = ClassUtil.classLoader.getResourceAsStream(propsName)) {
            getProperties().load(is);
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件" + propsName + "出现异常", e);
        }
    }


    public static void saveProperties(Properties prop, String fileName) throws Exception {
        if (StringUtils.isBlank(fileName)) {
            fileName = propertiesFile.getName();
        }
        String filePath = ClassUtil.getClasspath() + "/" + fileName;
        File file = new File(filePath);
        // FileWriter fileWriter = new FileWriter(filePath);
        if (!file.exists()) {
            FileUtil.createFile(file);
        }
        FileOutputStream fos = new FileOutputStream(filePath);
        prop.store(fos, "@author gaoxizhi");
        fos.close();
    }

}
