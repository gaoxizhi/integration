package net.gaox.javaSE2018.arithmetic.sort;

/**
 * @Description: <p>  </p>
 * @ClassName BubbleSort
 * @Author: gaox·Eric
 * @Date: 2019/3/31 18:15
 */
public class BubbleSort {
    public static void main(String[] args) {

        int[] array = {12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16};
        long[] time = new long[4];
        time[0] = (long) System.nanoTime();
        bobSort();
        time[1] = (long) System.nanoTime();
        chooseSort();
        time[2] = (long) System.nanoTime();
        insteaSort();
        time[3] = (long) System.nanoTime();

        for (int i = 1; i < 4; i++) {
            System.out.println(i + "用时：" + (time[i] - time[i - 1]));
        }
    }

    private static void insteaSort() {
        /**
         * 插入排序
         */
        int[] array = {12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16};

        for (int i = 1; i < array.length; i++) {
            int w = i;
            int temp = array[i];
            int j = i - 1;
            for (; (j >= 0) && (temp < array[j]); j--) {
                // 在第j位置找到比i位置还要小的数
                // 在已排序的数之后，向后移动
                array[j + 1] = array[j];
            }
            // 这里如果产生交换j的值已经改变，如果没有交换则将temp又赋值给原来的位置
            array[j + 1] = temp;
            for (int k = 0; k < array.length; k++) {
                System.out.print(array[k] + ",");
            }
            System.out.println();
        }

    }

    private static void chooseSort() {
        /**
         * 选择排序
         */
        int[] array = {12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16};
        for (int i = 0; i < array.length; i++) {
            int w = i;
            int temp = array[i];
            for (int j = i + 1; j < array.length; j++) {
                if (temp > array[j]) {
                    w = j;
                    temp = array[j];
                }
            }
            if (w != i) {
                array[w] = array[i];
                array[i] = temp;
            }
            for (int k = 0; k < array.length; k++) {
                System.out.print(array[k] + ",");
            }
            System.out.println();

        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
        System.out.println();
        System.out.println();
    }

    private static void bobSort() {
        /**
         * 冒泡排序 依次比较将最大的放到最后
         */
        int[] array = {12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16};

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            for (int k = 0; k < array.length; k++) {
                System.out.print(array[k] + ",");
            }
            System.out.println();
        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
        System.out.println();
        System.out.println();
    }

}

