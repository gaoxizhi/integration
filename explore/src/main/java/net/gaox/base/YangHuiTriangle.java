package net.gaox.base;

import java.util.Scanner;

/**
 * @Description: <p>  </p>
 * @ClassName YangHuiTriangle
 * @Author: gaox·Eric
 * @Date: 2019/3/31 16:23
 */
public class YangHuiTriangle {

    public static void main(String[] args) {
        int line;
        Scanner scanner = new Scanner(System.in);
        line = scanner.nextInt();
        // 初始化数组
        int[][] yharray = new int[line][line + 1];
        yharray[0][0] = 1;
        for (int i = 0; i < line; i++) {
            yharray[i][0] = 1;
            yharray[i][i] = 1;
            for (int j = 1; j < i; j++) {
                //咋还没实现！！！
            }
        }
        for (int i = 0; i < line; i++) {
            // 打印制表符
            for (int j = 0; j < (line - i); j++) {
                System.out.print("\t");
            }
            // 打印数值
            for (int j = 0; j < i + 1; j++) {
                if (i == 0) {
                    System.out.print("1");
                } else {
                    System.out.print(yharray[i][j]);
                }
            }
            System.out.println();
        }
    }
}
