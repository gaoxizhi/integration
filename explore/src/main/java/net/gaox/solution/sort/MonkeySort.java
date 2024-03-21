package net.gaox.solution.sort;

import com.google.common.primitives.Ints;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p> 乱搞排序-猴子算法 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 10:58
 */
public class MonkeySort {
    public static void monkeySort(int[] arrays) {
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

    public static void printSortInfo(List<Integer> temp, long count) {
        String array = temp.stream().map(Objects::toString).collect(Collectors.joining(", ", "[", "]"));
        String format = String.format("尝试计算第 %6d 次，当前顺序：%s。", count, array);
        System.out.println(format);
    }

}
