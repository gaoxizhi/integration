package net.gaox.jvm;

import lombok.extern.slf4j.Slf4j;
import net.gaox.base.RandomTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Thread.currentThread;

/**
 * <p> 类加载器 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-24 14:19
 */
@Slf4j
public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            InterruptedException {

        // 根加载器又称为 Bootstrap 类加载器，该类加载器是最为顶层的加载器，其没有任何父加载器
        // 是由 C++ 编写的，主要负责虚拟机核心类库的加载
        // 比如整个 java.lang 包都是由根加载器所加载的
        // 可以通过 "-Xbootclasspath" 来指定根加载器的路径
        // java.lang.String.class 的类加载器是"根加载器"，根加载器是获取不到引用的，因此输出为 null
        // log.info("Bootstrap: {}", String.class.getClassLoader().getClass().getSimpleName());

        // 根加载器所在的加载路径可以通过 sun.boot.class.path 这个系统属性来获得
        String property = System.getProperty("sun.boot.class.path");
        List<String> properties = Optional.ofNullable(property)
                .map(s -> s.split(":"))
                .map(s -> Arrays.stream(s).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        String propertyStrList = properties.stream().collect(Collectors.joining("\n", "[\n", "\n]"));
        log.info("sun.boot.class.path size = {}, list = {}", properties.size(), propertyStrList);

        // 扩展类加载器的父加载器是根加载器，它主要用于加载"JAVA_HOME"下的"jre\lb\ext"子目录里面的类库
        // 扩展类加载器是由纯 Java 语言实现的，它是"java.lang.URLClassLoader"的子类
        // 它的完整类名是"sun.misc.Launcher$ExtClassLoader"
        String extProperty = System.getProperty("java.ext.dirs");
        List<String> extProperties = Optional.ofNullable(extProperty)
                .map(s -> s.split(":"))
                .map(s -> Arrays.stream(s).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        String extPropertyStrList = extProperties.stream().collect(Collectors.joining("\n", "[\n", "\n]"));
        log.info("java.ext.dirs size = {}, list = {}", extProperties.size(), extPropertyStrList);

        // 应用加载器所在的加载路径可以通过 java.class.path 这个系统属性来获得
        String javaProperty = System.getProperty("java.class.path");
        List<String> javaProperties = Optional.ofNullable(javaProperty)
                .map(s -> s.split(":"))
                .map(s -> Arrays.stream(s).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        String javaPropertyStrList = javaProperties.stream().collect(Collectors.joining("\n", "[\n", "\n]"));
        log.info("java.class.path size = {}, list = {}", javaProperties.size(), javaPropertyStrList);

        // 获取启动类的类加载器 AppClassLoader
        log.info("main classLoader: {}", ClassLoaderTest.class.getClassLoader().getClass().getSimpleName());

        log.info("============================ gaoxClassLoader ============================");
        // 使用 自定义类加载器 GaoxClassLoader
        ClassLoader classLoader = new GaoxClassLoader();
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        log.info("currentThread contextClassLoader = {}", contextClassLoader.getClass().getSimpleName());

        Thread.currentThread().setContextClassLoader(classLoader);
        ClassLoader newContextClassLoader = currentThread().getContextClassLoader();
        log.info("new currentThread contextClassLoader = {}", newContextClassLoader.getClass().getSimpleName());

        // 指定子线程的类加载器
        Thread thread = new Thread(() -> {
            RandomTest randomTest = new RandomTest();
            log.info("inner thread classLoad = {}", randomTest.getClass().getClassLoader().getClass().getSimpleName());
        });
        thread.setContextClassLoader(classLoader);
        thread.start();


        // 不指定类加载器的父加载器，使用默认的根加载器
        ClassLoader gaoxClassLoader = new GaoxClassLoader("/Users/gaox/data/gaoxClassloader", null);

        // loadClass
        Class<?> loadClazz = gaoxClassLoader.loadClass("HelloWorld2");
        log.info("loadClazz classLoader = {}", loadClazz.getClassLoader().getClass().getSimpleName());
        Object loadClazzToString = loadClazz.newInstance();
        log.info("loadClazzToString = {}", loadClazzToString);
        Method method = loadClazz.getDeclaredMethod("welcome");
        // 设置私有方法的可访问性
        method.setAccessible(true);
        String result = (String) method.invoke(loadClazzToString);
        log.info("loadClazz method result: {}", result);

        // 不同实例的类加载器，加载出来的类对象
        ClassLoader gaoxClassLoader2 = new GaoxClassLoader("/Users/gaox/data/gaoxClassloader", null);
        Class<?> loadClazz2 = gaoxClassLoader2.loadClass("HelloWorld2");
        log.info("loadClazz2 classLoader = {}", loadClazz2.getClassLoader().getClass().getSimpleName());
        Object loadClazz1 = loadClazz2.newInstance();
        log.info("loadClazz1 = {}", loadClazz1);

        // 加载的两个类是不相同的对象
        log.info("loadClazz.hashCode = {}", Integer.toHexString(loadClazz.hashCode()));
        log.info("loadClazz2.hashCode = {}", Integer.toHexString(loadClazz2.hashCode()));
        log.info("loadClazz == loadClazz2 is {}", loadClazz == loadClazz2);

        // Class.forName 获取 类对象
        // ClassNotFoundException 找不到这个类
        /// String classForName = Optional.ofNullable(Class.forName("net.gaox.base.Base"))
        //         .map(s -> s.getClassLoader().getClass().getSimpleName())
        //         .orElse("ClassNotFound");
        // log.info("net.gaox.base.Base classLoader: {}", classForName);

    }

}
