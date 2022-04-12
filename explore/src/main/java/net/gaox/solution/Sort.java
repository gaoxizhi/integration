package net.gaox.solution;

import com.google.common.primitives.Ints;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Sort {

    public static void main(String[] args) {
        int[] array = {12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16};
        long[] time = new long[5];
        time[0] = System.nanoTime();
        bobSort(array);
        time[1] = System.nanoTime();
        chooseSort(array);
        time[2] = System.nanoTime();
        insteaSort(array);
        time[3] = System.nanoTime();
        monkeySort(array);
        time[4] = System.nanoTime();

        for (int i = 1; i < 5; i++) {
            System.out.println(i + "用时：" + (time[i] - time[i - 1]));
        }
    }

    /**
     * 插入排序
     */
    private static void insteaSort(int[] array) {
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

    /**
     * 选择排序
     */
    private static void chooseSort(int[] array) {
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

    /**
     * 冒泡排序 依次比较将最大的放到最后
     */
    private static void bobSort(int[] array) {
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

    private static void monkeySort(int[] arrays) {
        List<Integer> temp = Ints.asList(arrays);
        long count = 0;
        out:
        while (true) {
            // 高效重新排序
            Collections.shuffle(temp);
            int[] result = Ints.toArray(temp);
            count++;
            for (int i = 0; i < result.length - 1; i++) {
                if (result[i] > result[i + 1]) {
                    if (0 == count % 100) {
                        printSortInfo(temp, count);
                    }
                    continue out;
                }
            }
            // 证明了result是有序的，需要进行拷贝
            System.arraycopy(result, 0, arrays, 0, result.length);
            printSortInfo(temp, count);
            break;
        }
    }

    private static void printSortInfo(List<Integer> temp, long count) {
        String array = temp.stream().map(Objects::toString).collect(Collectors.joining(", ", "[", "]"));
        String format = String.format("尝试计算第 %6d 次，当前顺序：%s。", count, array);
        System.out.println(format);
    }
}
