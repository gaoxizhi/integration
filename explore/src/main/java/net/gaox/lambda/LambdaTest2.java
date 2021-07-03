package net.gaox.lambda;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/8/8 22:09
 */
public class LambdaTest2 {


    public static void main(String[] args) {

        //原始写法
        Thread oldThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello old method new Thread! ");
            }
        });
        oldThread.start();

        //lambda写法
        Thread newThread = new Thread(() -> System.out.println("hello new method new Thread! "));
        newThread.start();

        List<String> strings = asList("asd", "tgh", "oks", "lkc", "qae");
        //排序 匿名内部方式
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        //排序 使用String中的compareTo方法
        Collections.sort(strings, (o1, o2) -> o1.compareTo(o2));
        Collections.sort(strings, String::compareTo);
        strings.sort(String::compareTo);

        //对strings转大写
        List<String> peekUpper = strings.stream().map((s) -> {
            return s.toUpperCase();
        }).collect(Collectors.toList());
        List<String> mapUpper = strings.stream().map(String::toUpperCase).collect(Collectors.toList());

        /*
         * 语法说明
         * 1.当lambda表达式的参数个数只有一个，可以省略小括号
         * 2.当lambda表达式只包含一条语句时，可以省略大括号、return和语句结尾的分号
         * 3.方法引用写法 如String的toUpperCase方法，String::toUpperCase
         *
         * 方法引用
         *      objectName::instanceMethod
         *      ClassName::staticMethod
         *      ClassName::instanceMethod
         * 构造器引用
         *      构造器引用语法如下：ClassName::new
         *      BigDecimal::new等同于x->new BigDecimal(x)
         */

        // generate 生成一个无限长度的Stream，并且是是懒加载
        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<Integer> randomInt = Stream.generate(() -> ((int) (Math.random() * 1000) % 16));
        // 配合limit指定长度
        Stream<Integer> randimInt1024 = Stream.generate(() -> ((int) (Math.random() * 1000) % 16)).limit(1024);
        //和generator不同的是，其元素的生成是重复对给定的种子值(seed)调用用户指定函数来生成的。
        // 其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环
        Stream.iterate(1, item -> item + 1).limit(10).forEach(System.out::println);
        Stream.iterate(((int) (Math.random() * 1000) % 16), item -> item + 1).limit(10).forEach(System.out::println);
        //将内部的集合整合到一个里面
        List<List<Integer>> lists = Arrays.asList(
                Collections.singletonList(1),
                Arrays.asList(2, 3),
                asList(4, 5, 6)
        );
        List<Integer> sumInt = lists.stream().flatMap(Collection::stream).collect(Collectors.toList());
        // 0至n-1，对一个Stream进行截断操作，获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素
        List<Integer> limitInt = sumInt.stream().limit(5).collect(Collectors.toList());
        //n-1至-1，返回一个丢弃原Stream的前N个元素后剩下元素组成的新Stream，如果原Stream中包含的元素个数小于N，那么返回空Stream
        List<Integer> skipInt = sumInt.stream().skip(2).collect(Collectors.toList());

        //Lists是Guava中的一个工具类
        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("sum is:" + nums.stream().filter(Objects::nonNull).distinct().mapToInt(num -> num * 2)
                .peek(System.out::println).skip(2).limit(4).sum());

        List<Integer> numsWithoutNull = nums.stream().filter(num -> num != null).
                collect(() -> new ArrayList<Integer>(),
                        (list, item) -> list.add(item),
                        (list1, list2) -> list1.addAll(list2)
                );
        /*
         * 对一个元素是Integer类型的List，先过滤掉全部的null，然后把剩下的元素收集到一个新的List中。
         * 进一步看一下collect方法的三个参数，都是lambda形式的函数。
         * 第一个函数生成一个新的ArrayList实例；
         * 第二个函数接受两个参数，第一个是前面生成的ArrayList对象，二个是stream中包含的元素，
         *      函数体就是把stream中的元素加入ArrayList对象中。第二个函数被反复调用直到原stream的元素被消费完毕；
         * 第三个函数也是接受两个参数，这两个都是ArrayList类型的，函数体就是把第二个ArrayList全部加入到第一个中；
         */
        List<Integer> numsWithoutNull2 = nums.stream().filter(Objects::nonNull)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("ints sum is:" + numsWithoutNull.stream().reduce((sum, item) -> sum + item).get());
        System.out.printf("ints sum is:%d%n", numsWithoutNull.stream().reduce(Integer::sum).get());
        System.out.printf("ints sum is:%d%n", nums.stream().reduce(0, Integer::sum));
        /*
         * – allMatch：是不是Stream中的所有元素都满足给定的匹配条件
         * – anyMatch：Stream中是否存在任何一个元素满足匹配条件
         * – findFirst: 返回Stream中的第一个元素，如果Stream为空，返回空Optional
         * – noneMatch：是不是Stream中的所有元素都不满足给定的匹配条件
         * – max和min：使用给定的比较器（Operator），返回Stream中的最大|最小值
         */

        List<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(ints.stream().allMatch(item -> item < 5));
        ints.stream().max((o1, o2) -> o1.compareTo(o2)).ifPresent(System.out::println);
        ints.stream().max(Integer::compareTo).ifPresent(System.out::println);
        final LocalDate beginTime = LocalDate.of(2019, 5, 1);

        //最终列表
        ArrayList<ManageSort> list = new ArrayList<>();
        final LocalDate toDay = LocalDate.now();

        Random random = new Random(1000000);
        for (int i = 0; i <= beginTime.until(toDay, ChronoUnit.DAYS); i++) {
            ManageSort manageSort = new ManageSort().setTime(beginTime.plusDays(i)).setType("type" + i)
                    .setDateTime(new Date(System.currentTimeMillis() + random.nextLong()));
            list.add(manageSort);
        }

        //自定义排序规则：包含大小相等条件，日期（后-->前）
        list.sort((o1, o2) -> o1.getTime().isBefore(o2.getTime()) ? 1 : (o1.getTime().isAfter(o2.getTime()) ? -1 : 0));

        // 时间1排序
        Comparator<ManageSort> dateTimeComparator = (o1, o2) -> (int) (o1.getDateTime().getTime() - o2.getDateTime().getTime());
        // 时间2排序
        Comparator<ManageSort> dateComparator = Comparator.comparing(ManageSort::getTime);
        // type排序
        Comparator<ManageSort> typeComparator = Comparator.comparing(ManageSort::getType);
        // 时间1排序 逆序，时间2排序，type排序
        list.sort(dateTimeComparator.reversed().thenComparing(dateComparator).thenComparing(typeComparator));

        list.sort((o1, o2) -> {
            int compare1 = o2.getTime().isBefore(o1.getTime()) ? 1 : (o1.getTime().isAfter(o2.getTime()) ? -1 : 0);
            int compare2 = o1.getTime().compareTo(o2.getTime());
            int compare3 = o1.getType().compareTo(o2.getType());
            return -compare1 == 0 ? (compare2 == 0 ? (compare3 == 0 ? 0 : compare3) : compare2) : -compare1;
        });
    }

    //    Logger.getL
    @Data
    @Accessors(chain = true)
    static class ManageSort {
        private String type;
        private LocalDate time;
        private Date dateTime;
    }
}
