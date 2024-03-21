package net.gaox.solution.sort;

/**
 * <p> 选择排序 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 09:53
 */
public class ChooseSort {

    public static void chooseSort(int[] array) {
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
            System.out.println(SortUtil.getArrayString(array));

        }
        System.out.println(SortUtil.getArrayString(array));
    }

}
