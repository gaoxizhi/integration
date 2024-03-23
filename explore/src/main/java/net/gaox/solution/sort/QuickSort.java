package net.gaox.solution.sort;

/**
 * <p> 快速排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 10:10
 */
public class QuickSort {

    public static void quick(int[] array) {
        System.out.println(SortUtil.getArrayString(array));
        quickSort(array, 0, array.length);
        System.out.println(SortUtil.getArrayString(array));
    }


    /**
     * 快速排序算法
     *
     * @param array 待排序的数组
     * @param start 排序范围的起始索引
     * @param count 排序的数量
     */
    public static void quickSort(int[] array, int start, int count) {
        // last下标等于 count-1
        int first = start;
        int last = count - 1;
        // 基准值
        int pivotValue = array[first];

        while (first < last) {
            // 从右向左找第一个小于pivotValue的数
            while (first < last && array[last] >= pivotValue) {
                --last;
            }
            // last位置值小于pivotValue分界值 交换
            array[first] = array[last];
            System.out.println(SortUtil.getArrayString(array));
            // 从左向右找第一个大于pivotValue的数
            while (first < last && array[first] < pivotValue) {
                ++first;
            }
            // last位置值大于pivotValue分界值 交换
            array[last] = array[first];
            System.out.println(SortUtil.getArrayString(array));
        }

        // 将基准值放在中间
        array[first] = pivotValue;
        System.out.println(SortUtil.getArrayString(array));

        // 递归左右部分进行快排
        if (first > start) {
            quickSort(array, start, first);
        }
        if (first + 1 < count) {
            quickSort(array, first + 1, count);
        }
        System.out.println(SortUtil.getArrayString(array));
    }

}
