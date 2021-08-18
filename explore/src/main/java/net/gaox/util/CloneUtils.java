package net.gaox.util;

import java.io.*;
import java.util.List;

/**
 * <p> 拷贝工具包 </p>
 *
 * @author gaox·Eric
 * @date 2021/8/17 23:06
 */
public class CloneUtils {


    /**
     * 利用字节数组（ByteArray）输出（输入流）序列化实现深拷贝List
     * 字节数组输出（输入）流则是将其保存在一个字节数组的临时变量中，仅占用内存空间，用后会自动清除。
     *
     * @param src 源数组
     * @param <T> 范型
     * @return 范型的list
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类型找不到
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(src);

        final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        final ObjectInputStream ois = new ObjectInputStream(bis);
        @SuppressWarnings(("unchecked"))
        List<T> desc = (List<T>) ois.readObject();
        return desc;
    }
}
