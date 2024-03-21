package net.gaox.solution.sort;

/**
 * <p> 归并排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 10:28
 */
public class MergeSort {

    public static void mergeSort(int[] array) {
        mergeSortInternal(array, 0, array.length - 1);
    }

    /**
     * 递归调用的归并排序方法
     *
     * @param array 要排序的整型数组
     * @param low   排序范围的起始索引
     * @param high  排序范围的结束索引
     */
    public static void mergeSortInternal(int[] array, int low, int high) {
        // 当起始索引大于或等于结束索引时，表示该子数组已排序完成，递归终止
        if (low >= high) {
            return;
        }
        // 计算中间索引，将数组划分为两部分
        int mid = (low + high) / 2;
        // 递归地对左右半部分进行归并排序
        mergeSortInternal(array, low, mid);
        mergeSortInternal(array, mid + 1, high);
        // 合并左右两部分，使它们有序
        merge(array, low, mid, high);
    }

    /**
     * 合并两部分数组
     *
     * @param array 要合并的数组
     * @param start 合并范围的起始索引
     * @param mid   合并范围的中间索引，即第一部分的结束位置
     * @param end   合并范围的终止索引
     */
    public static void merge(int[] array, int start, int mid, int end) {
        // 第一、二部分的起始索引
        int s1 = start;
        int s2 = mid + 1;

        // 用于存储合并结果的临时数组
        int[] tmp = new int[end - start + 1];
        // 临时数组的当前索引
        int k = 0;

        // 循环，直到其中一个部分的所有元素都被处理完毕
        while (s1 <= mid && s2 <= end) {
            if (array[s1] <= array[s2]) {
                // 从第一个部分选择较小的元素
                tmp[k++] = array[s1++];
            } else {
                // 从第二个部分选择较小的元素
                tmp[k++] = array[s2++];
            }
        }

        // 处理第一个部分剩余的元素（如果有的话）
        while (s1 <= mid) {
            tmp[k++] = array[s1++];
        }

        // 处理第二个部分剩余的元素（如果有的话）
        while (s2 <= end) {
            tmp[k++] = array[s2++];
        }

        // 将合并后的结果复制回原数组
        System.arraycopy(tmp, 0, array, start, tmp.length);
    }

}
