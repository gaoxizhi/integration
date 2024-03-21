package net.gaox.code;

import java.io.UnsupportedEncodingException;

/**
 * <p> 字符编码 </p>
 *
 * @author gaox·Eric
 * @date 2019/8/4 19:00
 */
public class StringCode {
    public static void main(String[] args) {
        String test = "联通";
        System.out.println("测试字符串: " + test);
        try {
            // UTF-16
            String charsetU16 = "UTF-16";
            // UTF-16BE编码格式 大端形式编码
            String charsetU16BE = "UTF-16BE";
            // UTF-16LE编码格式 小端形式编码
            String charsetU16LE = "UTF-16LE";
            // gbk编码格式
            String charsetGbk = "gbk";


            byte[] bytesUTF16 = test.getBytes(charsetU16);
            printHex(charsetU16, bytesUTF16);

            byte[] bytesUTF16BE = test.getBytes(charsetU16BE);
            printHex(charsetU16BE, bytesUTF16BE);

            byte[] bytesUTF16LE = test.getBytes(charsetU16LE);
            printHex(charsetU16LE, bytesUTF16LE);

            byte[] bytesGbk = test.getBytes(charsetGbk);
            printHex(charsetGbk, bytesGbk);
            System.out.println();

            String strUTF16, strUTF16BE, strUTF16LE, strGbk;
            // 自带大小端编码格式的字节数组转化
            strUTF16 = new String(bytesUTF16, charsetU16);
            strUTF16BE = new String(bytesUTF16, charsetU16BE);
            strUTF16LE = new String(bytesUTF16, charsetU16LE);
            strGbk = new String(bytesUTF16, charsetGbk);
            System.out.println("strUTF16:" + strUTF16);
            System.out.println("strUTF16BE:" + strUTF16BE);
            System.out.println("strUTF16LE:" + strUTF16LE);
            System.out.println("strGbk:" + strGbk);
            System.out.println();

            // 大端编码格式的字节数组转化
            strUTF16 = new String(bytesUTF16BE, charsetU16);
            strUTF16BE = new String(bytesUTF16BE, charsetU16BE);
            strUTF16LE = new String(bytesUTF16BE, charsetU16LE);
            strGbk = new String(bytesUTF16BE, charsetGbk);
            System.out.println("strUTF16:" + strUTF16);
            System.out.println("strUTF16BE:" + strUTF16BE);
            System.out.println("strUTF16LE:" + strUTF16LE);
            System.out.println("strGbk:" + strGbk);
            System.out.println();

            // 小端编码格式的字节数组转化
            strUTF16 = new String(bytesUTF16LE, charsetU16);
            strUTF16BE = new String(bytesUTF16LE, charsetU16BE);
            strUTF16LE = new String(bytesUTF16LE, charsetU16LE);
            strGbk = new String(bytesUTF16LE, charsetGbk);
            System.out.println("strUTF16:" + strUTF16);
            System.out.println("strUTF16BE:" + strUTF16BE);
            System.out.println("strUTF16LE:" + strUTF16LE);
            System.out.println("strGbk:" + strGbk);
            System.out.println();

            // 小端编码格式的字节数组转化
            strUTF16 = new String(bytesGbk, charsetU16);
            strUTF16BE = new String(bytesGbk, charsetU16BE);
            strUTF16LE = new String(bytesGbk, charsetU16LE);
            strGbk = new String(bytesGbk, charsetGbk);
            System.out.println("strUTF16:" + strUTF16);
            System.out.println("strUTF16BE:" + strUTF16BE);
            System.out.println("strUTF16LE:" + strUTF16LE);
            System.out.println("strGbk:" + strGbk);
            System.out.println();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void printHex(String type, byte[] data) {
        StringBuilder builder = new StringBuilder();
        builder.append("type: ").append(type).append(":\t");
        for (byte datum : data) {
            builder.append("0x").append(Integer.toHexString(datum & 0xFF).toUpperCase()).append(" ");
        }
        System.out.println(builder);
    }
}