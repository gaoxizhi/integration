package net.gaox.solution.sort;

/**
 * <p> 希尔排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 09:46
 */
public class ShellSort {

    public static void shellSort(int[] array) {
        // 增量数组
        int[] drr = {5, 3, 1};
        for (int i = 0; i < drr.length; i++) {
            shell(array, drr[i]);
        }
        System.out.println(SortUtil.getArrayString(array));
        System.out.println();
    }

    public static void shell(int[] array, int gap) {
        for (int i = gap; i < array.length; i++) {
            int tmp = array[i];
            int j = i - gap;
            for (; j >= 0; j = j - gap) {
                if (array[j] > tmp) {
                    array[j + gap] = array[j];
                } else {
                    break;
                }
            }
            array[j + gap] = tmp;
            System.out.printf("gap = %2d, i = %2d : %s \n", gap, i, SortUtil.getArrayString(array));
        }
    }

}
