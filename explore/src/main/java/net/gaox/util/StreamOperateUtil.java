package net.gaox.util;

import java.io.*;

/**
 * <p> 通过 ByteArray 字节流拷贝流 </p>
 * 用于保存需要关闭流操作，但希望保存流内容
 *
 * @author gaox·Eric
 * @date 2021/8/17 23:11
 */

public class StreamOperateUtil {
    public static void main(String[] args) throws FileNotFoundException {

        InputStream input = new FileInputStream("/User/gaox/json.json");

        ByteArrayOutputStream copyStream = cloneInputStream(input);

        // 打开两个新的输入流
        InputStream stream1 = new ByteArrayInputStream(copyStream.toByteArray());
        InputStream stream2 = new ByteArrayInputStream(copyStream.toByteArray());

    }

    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream copyStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                copyStream.write(buffer, 0, len);
            }
            copyStream.flush();
            return copyStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}