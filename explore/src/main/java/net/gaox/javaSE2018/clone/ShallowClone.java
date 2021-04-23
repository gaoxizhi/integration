package net.gaox.javaSE2018.clone;

/**
 * @Description: <p>  </p>
 * @ClassName ShallowClone
 * @Author: gaox·Eric
 * @Date: 2019/3/31 19:00
 */
public class ShallowClone {
    public static void main(String[] args) {
        /**
         * 二维数组的赋值
         * 1.初始化
         * 2.依次赋值
         * 3.copy/clone
         */
        int[][] arr = {{12, 54, 76, 33}, {65, 78, 98, 31}, {32, 71, 16, 46}, {25, 46, 78, 13}};

        int[][] arr1 = new int[10][10];
        //这里的克隆只是浅复制，牵一发而动全身
        //arr1 = arr.clone();
        //arr = arr1.clone();
        //arr1[9][9] = 10;
        System.out.println("数组输出：");
        for (int[] i : arr) {
            for (int j : i) {
                System.out.print(j + "  ");
            }
            System.out.println();
        }
        System.out.println("arr.length: " + arr.length);
        System.out.println("arr[1].length: " + arr[1].length);
    }
}

