package net.gaox.solution.sort;

/**
 * <p> 快排优化 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-24 01:01
 */
public class QuickSortOptimized {

    public static void quick(int[] array) {
        System.out.println(SortUtil.getArrayString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println(SortUtil.getArrayString(array));
    }

    /**
     * 快速排序算法
     *
     * @param array 待排序的数组
     * @param start 排序范围的起始索引
     * @param end   排序范围的结束索引
     */
    public static void quickSort(int[] array, int start, int end) {
        if (array == null || start >= end) {
            return;
        }

        // 使用尾递归优化和三向切分
        quickSortHelper(array, start, end);
    }

    private static void quickSortHelper(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        // 分区并返回枢轴的最终位置
        int pivotIndex = partition(array, start, end);
        // 递归处理左右半部分
        quickSortHelper(array, start, pivotIndex - 1);
        quickSortHelper(array, pivotIndex + 1, end);
    }

    private static int partition(int[] array, int start, int end) {
        // 以起始元素为基准值
        int pivot = array[start];
        int left = start + 1;
        int right = end;

        while (true) {
            while (left <= right && array[left] < pivot) {
                left++;
            }
            while (left <= right && array[right] >= pivot) {
                right--;
            }
            if (left > right) {
                break;
            }
            // 交换left和right位置的元素
            swap(array, left, right);
        }

        // 将基准值放到正确的位置
        swap(array, start, right);
        System.out.println(SortUtil.getArrayString(array));
        return right;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        System.out.println(SortUtil.getArrayString(array));
    }

}
