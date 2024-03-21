package net.gaox.solution.sort;

/**
 * <p> 冒泡排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 09:55
 */
public class BubbleSort {


    /**
     * 冒泡排序 依次比较将最大的放到最后
     */
    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            System.out.println(SortUtil.getArrayString(array));
        }
        System.out.println(SortUtil.getArrayString(array));
        System.out.println();
    }

}
