package net.gaox.reflect.test;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p> 类的加载器 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-22 22:37
 */
public class ClassLoaderTest {

    @Test
    public void test1() {
        // 于自定义类，使用系统类加载器进行加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);

        // 用系统类加载器的 getParent()：获取扩展类加载器
        ClassLoader classLoader1 = classLoader.getParent();
        System.out.println(classLoader1);

        // 用扩展类加载器的 getParent()：无法获取引导类加载器
        // 导类加载器主要负责加载java的核心类库，无法加载自定义类的。
        ClassLoader classLoader2 = classLoader1.getParent();
        System.out.println(classLoader2);

        ClassLoader classLoader3 = String.class.getClassLoader();
        System.out.println(classLoader3);
    }


    /**
     * Properties：用来读取配置文件。
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        Properties pros = new Properties();
        // 此时的文件默认在当前的module下。
        // 读取配置文件的方式一：
        FileInputStream fis = new FileInputStream("jdbc.properties");
        pros.load(fis);
        //
        // 读取配置文件的方式二：使用ClassLoader
        // 配置文件默认识别为：当前module的src下
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("jdbc2.properties");
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        System.out.println("user = " + user + ",password = " + password);
    }
}
