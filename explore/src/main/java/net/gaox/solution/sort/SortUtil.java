package net.gaox.solution.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p> 排序工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 09:56
 */
public class SortUtil {

    /**
     * 获取数组字符串
     *
     * @param array 数组
     * @return str
     */
    public static String getArrayString(int[] array) {
        return Arrays.stream(array).mapToObj(s -> String.format("%4d", s))
                .collect(Collectors.joining(","));
    }

}
