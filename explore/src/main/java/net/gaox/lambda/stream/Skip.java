package net.gaox.lambda.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p> 数组等距分割 </p>
 *
 * @author gaox·Eric
 * @date 2020/11/2 15:16
 */
public class Skip {

    /**
     * 计算切分次数
     *
     * @param length 长度
     * @param size   等分长度
     * @return 切分次数
     */
    private static Integer countStep(Integer length, Integer size) {
        return (length + size - 1) / size;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        System.out.println("list = " + list);

        List<List<Integer>> assign1 = assign(list, 3);
        List<List<Integer>> assign2 = assign2(list, 3);

        System.out.println("assign1.equals(assign1) = " + assign1.equals(assign1));
        System.out.println("assign1 = " + assign1);
        System.out.println("assign2 = " + assign2);

        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 6, 1, 2, 3, 8, 5, 1, 2, 3, 4, 5, 6, 1, 2, 3, 9, 5, 0);
        List<List<Integer>> averageAssign = averageAssign(source, 10);
        System.out.println("source = " + source);
        System.out.println("averageAssign = " + averageAssign);
        averageAssign.forEach(s -> System.out.print(s.size()));
        String split = averageAssign.stream().parallel().map(List::size).map(Object::toString).collect(
                Collectors.joining("、", "分割后长度为", "。"));
        System.out.println(split);
    }

    /**
     * 将一个list分成多个长度为n的list
     * 方法一：使用流遍历操作
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> assign(List<T> source, int n) {

        int limit = countStep(source.size(), n);
        // 生成0～limit的整型数组
        List<List<T>> splitList = Stream.iterate(0, m -> m + 1).limit(limit).map(i ->
                source.stream().skip(i * n).limit(n).collect(Collectors.toList())
        ).collect(Collectors.toList());

        return splitList;
    }

    /**
     * 将一个list分成多个长度为n的list
     * 方法二：获取分割后的集合
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> assign2(List<T> source, int n) {

        int limit = countStep(source.size(), n);
        List<List<T>> splitList = Stream.iterate(0, m -> m + 1).limit(limit).parallel()
                .map(a -> source.stream().skip(a * n).limit(n).parallel().collect(Collectors.toList()))
                .collect(Collectors.toList());

        return splitList;
    }


    /**
     * 将一个list均分成n个list
     *
     * @param source 源数组
     * @param n      分割合计数
     * @return 分割结果
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        // 长度/等分个数
        // 商 分割后基本个数
        // 余数 所在位置在余数内，加一个对象
        int quotient = source.size() / n;
        int remainder = source.size() % n;
        List<List<T>> collect = Stream.iterate(0, m -> m + 1).limit(n).parallel()
                // 如果s<remainder 每个单元需要加一个元素
                // s*quotient +s 当s小于remainder时
                .map(s -> source.stream().skip(s * quotient + (s < remainder ? s : 0)).limit(quotient + (s < remainder ? 1 : 0))
                        .parallel().collect(Collectors.toList())
                ).collect(Collectors.toList());

        return collect;
    }
}
