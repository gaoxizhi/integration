package net.gaox.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author gaox
 */
public class ReadBuffered {

    public static void main(String[] args) throws Exception {
        String file = "file/0910.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String[] readStringFromFile = new String[32];
        int i = 0;
        while ((readStringFromFile[i++] = br.readLine()) != null) {
        }
        for (String s : readStringFromFile) {
            if (s != null) {
                System.out.println(s);
            }
        }
    }
}
