package net.gaox.jvm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p> 自定义简单的基于磁盘的 ClassLoader 类加载器 </p>
 * 自定义类加载器必须是 ClassLoader 的直接或者间接子类
 *
 * @author gaox·Eric
 * @date 2024-01-24 15:07
 */
public class GaoxClassLoader extends ClassLoader {

    /**
     * 定义默认的 class 存放路径
     */
    private final static Path DEFAULT_CLASS_DIR = Paths.get("/Users/gaox/data", "gaoxClassloader");

    /**
     * 指定一个特定的磁盘目录
     */
    private final Path classDir;

    /**
     * 使用默认的 class 路径
     */
    public GaoxClassLoader() {
        super();
        this.classDir = DEFAULT_CLASS_DIR;
    }

    /**
     * 允许传入指定路径的 class 路径
     *
     * @param classDir class 路径
     */
    public GaoxClassLoader(String classDir) {
        super();
        this.classDir = Paths.get(classDir);
    }

    /**
     * 指定 class 路径的同时，指定父类加载器
     *
     * @param classDir class 路径
     * @param parent   父类加载器
     */
    public GaoxClassLoader(String classDir, ClassLoader parent) {
        super(parent);
        this.classDir = Paths.get(classDir);
    }

    /**
     * 通过将类的全名称转换成文件的全路径 [至关重要的步骤]
     *
     * @param name class 全路径
     * @return class
     * @throws ClassNotFoundException e
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 读取 class 的二进制数据
        byte[] classBytes = this.readClassBytes(name);
        // 如果数据为 null，或者没有读到任何信息，则抛出 ClassNotFoundException 异常
        if (0 == classBytes.length) {
            throw new ClassNotFoundException("Can not load the class " + name);
        }
        // 调用 defineClass 方法定义 class
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    /**
     * 将class文件读入内存
     *
     * @param name class 全路径
     * @return class 字节数组
     * @throws ClassNotFoundException
     */
    private byte[] readClassBytes(String name) throws ClassNotFoundException {
        // 将包名分隔符转换为文件路径分隔符
        String classPath = name.replace(".", "/");
        Path classFullPath = classDir.resolve(Paths.get(classPath + ".class"));
        if (!classFullPath.toFile().exists()) {
            throw new ClassNotFoundException("The class " + name + " not found.");
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Files.copy(classFullPath, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException("load the class " + name + " occur error.", e);
        }
    }

    @Override
    public String toString() {
        return "GaoxClassLoader";
    }

}