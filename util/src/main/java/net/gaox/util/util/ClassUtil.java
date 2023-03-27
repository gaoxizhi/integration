package net.gaox.util.util;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> ClassPath 工具类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-27 01:08
 */
@Slf4j
public class ClassUtil {

    private static ClassLoader classLoader = getDefaultClassLoader();

    /**
     * 获取 ClassLoader
     */
    public static ClassLoader getDefaultClassLoader() {
        if (null != classLoader) {
            return classLoader;
        }

        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception ex) {
            log.error(" Thread.currentThread() 获取 ClassLoader 出错：", ex);
        }
        if (null == classLoader) {
            classLoader = PropertiesUtil.class.getClassLoader();
            if (null == classLoader) {
                try {
                    classLoader = ClassLoader.getSystemClassLoader();
                } catch (Exception ex) {
                    log.error(" ClassLoader.getSystemClassLoader() 获取 ClassLoader 出错：", ex);
                }
            }
        }
        return classLoader;
    }

    /**
     * 获取classpath
     */
    public static String getClasspath() {
        String classpath = null;
        try {
            classpath = getDefaultClassLoader().getResource("/").getPath();
        } catch (Exception e) {
            try {
                classpath = getDefaultClassLoader().getResource("").getPath();
            } catch (Exception ex) {
                log.error(" classpath 初始化失败：", ex);
            }
        }
        return classpath;
    }
}
