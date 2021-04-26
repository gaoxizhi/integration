package net.gaox.lambda;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p> 流生成器 </p>
 *
 * @author gaox·Eric
 * @date 2021/4/26 21:16
 */
public class GenerateList {
    public static void main(String[] args) {

        /**
         * 有限序列 列表
         */
        // 以 IntStream 为例，继承自 BaseStream 同级还有 LongStream 和 DoubleStream <br>
        // 收集方式（必要）： boxed（转换还需要map） 等价于 mapToObj
        List<Integer> intStreamRangeList = IntStream.range(0, 12).mapToObj(s -> s).collect(Collectors.toList());

        List<Integer> intStreamRangeClosedList = IntStream.rangeClosed(0, 12).boxed().collect(Collectors.toList());
        List<Integer> intStreamRangeClosedList2 = IntStream.range(0, 12).mapToObj(i -> 2 * i).collect(Collectors.toList());
        List<Integer> intStreamIterate = IntStream.iterate(0, i -> i += 2).limit(15).boxed().collect(Collectors.toList());

        List<Integer> streamIterate = Stream.iterate(0, i -> i += 2).limit(32).collect(Collectors.toList());
        List<Integer> streamGenerateSupplier = Stream.generate(new Supplier<Integer>() {
            Integer value = 0;

            @Override
            public Integer get() {
                return this.value += 2;
            }
        }).limit(20).collect(Collectors.toList());


        /**
         * 无限无序 列表
         * 有必要使用limit截取，限制流的长度
         */
        // IntStream 类型必须匹配
        List<Integer> intStreamGenerate = IntStream.generate(() -> new Random().nextInt(10))
                .limit(20).boxed().collect(Collectors.toList());
        List<Integer> double2IntStreamGenerate = IntStream.generate(() -> (int) new Random().nextDouble())
                .limit(20).boxed().collect(Collectors.toList());
        List<Double> streamGenerate = Stream.generate(() -> new Random().nextDouble())
                .limit(20).collect(Collectors.toList());
        List<Integer> streamGenerateSimilar = Stream.generate(() -> 1).limit(5).collect(Collectors.toList());
        System.out.println("'拦'精灵！");

    }
}
