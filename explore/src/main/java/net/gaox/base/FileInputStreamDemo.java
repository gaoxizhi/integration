package net.gaox.base;

import java.io.*;

/**
 * @author gaox
 */
public class FileInputStreamDemo {

    public static void main(String[] args) throws IOException {
        String file = "file/0910.txt";
        FileOutputStream fso = new FileOutputStream(file);
        DataOutputStream dso = new DataOutputStream(fso);
        for (int i = 0; i < 1024; i++) {
            if (i % 64 == 0) {
                if (i != 0) {
                    dso.write(10);
                }
            }
            char a = (char) (Math.random() * 26 + 97);
            dso.write(a);
        }
        //
        //dso.write('\0');

        FileInputStream fS = new FileInputStream(file);
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
    }

}
