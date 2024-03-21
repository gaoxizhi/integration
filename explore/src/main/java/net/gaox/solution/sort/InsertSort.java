package net.gaox.solution.sort;

/**
 * <p> 插入排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 09:56
 */
public class InsertSort {

    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            for (; (j >= 0) && (temp < array[j]); j--) {
                // 在第j位置找到比i位置还要小的数
                // 在已排序的数之后，向后移动
                array[j + 1] = array[j];
            }
            // 这里如果产生交换j的值已经改变，如果没有交换则将temp又赋值给原来的位置
            array[j + 1] = temp;
            System.out.println(SortUtil.getArrayString(array));
        }
        System.out.println(SortUtil.getArrayString(array));
    }

    /**
     * 插入排序法
     * 已经排好序的数组，用插入排序节省时间
     *
     * @param array 数组
     */
    public static void insertSort2(int[] array) {
        for (int i = 0; i < array.length; i++) {
            // 将a[i]向前移动，移动到前面一个值比它小，或者数组开头
            for (int j = i; j > 0; j--) {
                // 当后面的值<前面的值
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                } else {
                    break;
                }
            }
            System.out.println(SortUtil.getArrayString(array));
        }
        System.out.println(SortUtil.getArrayString(array));
    }

}
