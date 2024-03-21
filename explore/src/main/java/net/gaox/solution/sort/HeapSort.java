package net.gaox.solution.sort;

/**
 * <p> 堆排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 09:37
 */
public class HeapSort {

    public static void heapSort(int[] array) {
        crateBigHeap(array);
        int end = array.length - 1;
        while (end > 0) {
            int tmp = array[0];
            array[0] = array[end];
            array[end] = tmp;
            adjustDown(array, 0, end);
            end--;
        }
        System.out.println(SortUtil.getArrayString(array));
    }

    public static void crateBigHeap(int[] array) {
        for (int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            adjustDown(array, i, array.length);
        }
    }

    public static void adjustDown(int[] array, int parent, int len) {
        int child = 2 * parent + 1;
        // child<len 说明有左孩子
        while (child < len) {
            // child + 1 < len 判断是 当前是否 有右孩子
            if (child + 1 < len && array[child] < array[child + 1]) {
                child++;
            }
            // child 下标 一定是 左右孩子的最大值下标
            if (array[child] > array[parent]) {
                int tmp = array[child];
                array[child] = array[parent];
                array[parent] = tmp;
                parent = child;
                child = 2 * parent + 1;
            } else {
                // 因为是从最后一棵树开始调整的  只要我们找到了这个
                // this.elem[child] <= this.elem[parent]   后续就不需要循环了
                // 后面的都已经是大根堆了
                break;
            }
        }
    }

}
