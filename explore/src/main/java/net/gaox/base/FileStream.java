package net.gaox.base;

import java.io.*;
import java.util.Random;

/**
 * @Description: <p>  </p>
 * @ClassName FileStream
 * @Author: gaox·Eric
 * @Date: 2019/3/31 16:45
 */
public class FileStream {

    public static void main(String[] args) {
//        readFileInDisk("file/gaoxizhi.txt");
//        writeRubbishToFile("file/rubbishData.txt",252);
        writeContextToFile("file/hello.txt", "hello 高");
    }

    /**
     * 读取并打印文件
     *
     * @param fileName
     */
    public static void readFileInDisk(String fileName) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String[] read_string_from_file = new String[32];
            int i = 0;
            while ((read_string_from_file[i++] = br.readLine()) != null) {
            }
            for (String s : read_string_from_file) {
                if (s != null) {
                    System.out.println(s);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除硬盘，原理：写脏数据
     *
     * @param fileName
     * @param fileSize
     */
    public static void writeRubbishToFile(String fileName, long fileSize) {
        FileOutputStream fso = null;
        try {
            fso = new FileOutputStream(fileName);

            DataOutputStream dso = new DataOutputStream(fso);
            for (int i = 0; i < fileSize; i++) {
                if (i % 64 == 0) {
                    if (i != 0) {
                        dso.write(10);
                    }
                }
                char a = (char) (new Random().nextInt(26) + 97);
                dso.write(a);
            }

            dso.write('\0');

            FileInputStream fS = new FileInputStream(fileName);
            DataInputStream dS = new DataInputStream(fS);

//		Byte si = dS.readByte();
//		System.out.println(Character.toLowerCase((char)(byte)si));

            byte[] b = new byte[1024];
            fS.read(b, 0, 200);
            for (byte sss : b) {
                System.out.print((char) sss);
            }
            //System.out.println(b.toString());

            //System.out.println((1>3?"eee":"efef"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeContextToFile(String fileName, String context) {
        FileOutputStream fso = null;
        try {
            fso = new FileOutputStream(fileName);

            DataOutputStream dso = new DataOutputStream(fso);
            for (int i = 0; i < context.length(); i++) {
                if (i % 64 == 0) {
                    if (i != 0) {
                        dso.write(10);
                    }
                }
                char a = (char) (new Random().nextInt(26) + 97);
                dso.write(a);
            }

            for (int i = 0; i < context.length(); i++) {
                dso.write(context.charAt(i));
            }
            dso.write('\0');

            FileInputStream fS = new FileInputStream(fileName);
            DataInputStream dS = new DataInputStream(fS);

//		Byte si = dS.readByte();
//		System.out.println(Character.toLowerCase((char)(byte)si));

            byte[] b = new byte[1024];
            fS.read(b, 0, 200);
            for (byte sss : b) {
                System.out.print((char) sss);
            }
            //System.out.println(b.toString());

            //System.out.println((1>3?"eee":"efef"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
