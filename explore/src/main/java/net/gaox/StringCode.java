package net.gaox;

import java.io.UnsupportedEncodingException;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/8/4 19:00
 */
public class StringCode {
    public static void main(String[] args) {
        String test = "代码";

        try {
            //UTF-16BE编码格式 大端形式编码
            byte[] bytesUTF16BE = test.getBytes("UTF-16BE");
            printHex("UTF-16BE", bytesUTF16BE);

            //UTF-16LE编码格式 小端形式编码
            byte[] bytesUTF16LE = test.getBytes("UTF-16LE");
            printHex("UTF-16LE", bytesUTF16LE);

            //UTF-16
            byte[] bytesUTF16 = test.getBytes("UTF-16");
            printHex("UTF-16", bytesUTF16);

            //大端编码格式的字节数组转化
            String strUTF16BE = new String(bytesUTF16BE, "UTF-16BE");
            String strUTF16LE = new String(bytesUTF16BE, "UTF-16LE");
            String strUTF16 = new String(bytesUTF16BE, "UTF-16");
            System.out.println("strUTF16BE:" + strUTF16BE);
            System.out.println("strUTF16LE:" + strUTF16LE);
            System.out.println("strUTF16:" + strUTF16);

            //小端编码格式的字节数组转化
            strUTF16BE = new String(bytesUTF16LE, "UTF-16BE");
            strUTF16LE = new String(bytesUTF16LE, "UTF-16LE");
            strUTF16 = new String(bytesUTF16LE, "UTF-16");
            System.out.println("strUTF16BE:" + strUTF16BE);
            System.out.println("strUTF16LE:" + strUTF16LE);
            System.out.println("strUTF16:" + strUTF16);

            //自带大小端编码格式的字节数组转化
            strUTF16BE = new String(bytesUTF16, "UTF-16BE");
            strUTF16LE = new String(bytesUTF16, "UTF-16LE");
            strUTF16 = new String(bytesUTF16, "UTF-16");
            System.out.println("strUTF16BE:" + strUTF16BE);
            System.out.println("strUTF16LE:" + strUTF16LE);
            System.out.println("strUTF16:" + strUTF16);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void printHex(String type, byte[] data) {
        StringBuilder builder = new StringBuilder();
        builder.append("type:").append(type).append(":   ");
        for (byte datum : data) {
            builder.append("0x").append(Integer.toHexString(datum & 0xFF).toUpperCase()).append(" ");
        }
        System.out.println(builder);
    }
}