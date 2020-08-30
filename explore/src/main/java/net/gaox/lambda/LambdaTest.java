package net.gaox.lambda;

import com.google.common.collect.ImmutableList;
import net.gaox.lambda.entity.SysUser;
import net.gaox.lambda.entity.enums.EnumDel;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/8/8 22:09
 */
public class LambdaTest {
    public static void main(String[] args) {
        ArrayList<SysUser> list = new ArrayList<>();
        list.add(new SysUser().setId(1L).setPhone("110").setEmail("1").setDelFlag(EnumDel.DELETE).setCreateTime(LocalDateTime.now().plusHours(1)));
        list.add(new SysUser().setId(5L).setPhone("120").setEmail("1").setDelFlag(EnumDel.NORMAL).setCreateTime(LocalDateTime.now().plusHours(2)));
        list.add(new SysUser().setId(2L).setPhone("110").setEmail("g").setDelFlag(EnumDel.DELETE).setCreateTime(LocalDateTime.now().plusHours(3)));
        list.add(new SysUser().setId(3L).setPhone("119").setEmail("q").setDelFlag(EnumDel.DELETE).setCreateTime(LocalDateTime.now().plusHours(4)));

        //collect是一个终端操作,它接收的参数是将流中的元素累积到汇总结果的各种方式(称为收集器)
        /*
         * averagingDouble:求平均值，Stream的元素类型为double
         * averagingInt:求平均值，Stream的元素类型为int
         * averagingLong:求平均值，Stream的元素类型为long
         * counting:Stream的元素个数
         * maxBy:在指定条件下的，Stream的最大元素
         * minBy:在指定条件下的，Stream的最小元素
         * reducing: reduce操作，从一个作为累加器的初始值开始,利用binaryOperator与流中的元素逐个结合,从而将流归约为单个值
         * summarizingDouble:统计Stream的数据(double)状态，其中包括count，min，max，sum和平均。
         * summarizingInt:统计Stream的数据(int)状态，其中包括count，min，max，sum和平均。
         * summarizingLong:统计Stream的数据(long)状态，其中包括count，min，max，sum和平均。
         * summingDouble:求和，Stream的元素类型为double
         * summingInt:求和，Stream的元素类型为int
         * summingLong:求和，Stream的元素类型为long
         */

        List<SysUser> collectToList = list.stream().collect(Collectors.toList());
        System.out.println(collectToList);
        Set<SysUser> collectToSet = list.stream().collect(Collectors.toSet());
        System.out.println(collectToSet);
        ArrayList<SysUser> collectToAppoint = list.stream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(collectToAppoint);
        ArrayList<String> collectingAndThen = list.stream().map(SysUser::getPhone).collect(collectingAndThen(toSet(), ArrayList::new));
        ArrayList<String> setToArraysList = list.stream().map(SysUser::getPhone).collect(toSet()).stream().collect(toCollection(ArrayList::new));
        ArrayList<String> set = new ArrayList<>(list.stream().map(SysUser::getPhone).collect(toSet()));
        ArrayList<String> distinct = list.stream().map(SysUser::getPhone).distinct().collect(Collectors.toCollection(ArrayList::new));
        //获取email分组，获得各分组中id最大值
        Map<String, SysUser> collect5 = list.stream().collect(
                groupingBy(SysUser::getEmail,
                        collectingAndThen(maxBy(Comparator.comparingLong(SysUser::getId)), Optional::get)));
        Map<String, SysUser> collect51 = list.stream().collect(
                Collectors.toMap(SysUser::getEmail, Function.identity(), BinaryOperator.maxBy(Comparator.comparingLong(SysUser::getId))));
        SysUser collect4 = list.stream().collect(collectingAndThen(maxBy(Comparator.comparing(SysUser::getId)), Optional::get));

        //计数stream中元素的个数
        Long collectCountList = (long) list.size();
        Long collectCountList1 = list.stream().count();
        Long collectCountList2 = list.stream().collect(counting());
        System.out.println(collectCountList);
        //对stream中元素指定属性求和
        Long collectSum1 = list.stream().collect(summingLong(SysUser::getId));
        Long collectSum = list.stream().mapToLong(SysUser::getId).sum();
        System.out.println(collectSum);
        //对stream中元素指定属性求平均值
        Double collectAvg = list.stream().collect(averagingLong(SysUser::getId));
        System.out.println(collectAvg);
        //对stream中元素指定属性拼接（指定分割符，前缀，后缀）
        String collectJoin = list.stream().map(SysUser::getUserName).collect(joining(" | ", "Start-- ", " --End"));
        System.out.println(collectJoin);
        Optional<SysUser> collectMax1 = list.stream().collect(maxBy(Comparator.comparingLong(SysUser::getId)));
        Optional<SysUser> collectMax = list.stream().max(Comparator.comparingLong(SysUser::getId));
        System.out.println(collectMax);
        Optional<SysUser> collectMin1 = list.stream().collect(minBy(Comparator.comparingLong(SysUser::getId)));
        Optional<SysUser> collectMin = list.stream().min(Comparator.comparingLong(SysUser::getId));
        System.out.println("collectMax is " + collectMax + ", collectMin is " + collectMin);
        //  .reduce(BigDecimal.ZERO, BigDecimal::add))
        Long reduceSum = list.stream().map(SysUser::getId).reduce(0L, Long::sum);
        System.out.println(reduceSum);

        //collectingAndThen
        //转换函数返回的类型

        //去除phone相同的
        ArrayList<SysUser> collectComparing = list.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(Comparator.comparing(SysUser::getPhone))), ArrayList::new)
        );
        System.out.println(collectComparing);

        /**
         * List -> Map
         *
         * toMap 如果集合对象有重复的key，会报错Duplicate key ....
         * 可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
         */
        Map<String, SysUser> samePhone = list.stream().collect(toMap(SysUser::getPhone, a -> a, (k1, k2) -> k1));
        samePhone.forEach((s, l) -> System.out.printf("手机号：%s，用户列表【%s】。\n", s, l));

        //包裹另一个转换器,对其结果应用转换函数
        Integer collect3 = list.stream().collect(collectingAndThen(toList(), List::size));
        System.out.println(collect3);

        ArrayList<SysUser> peekList1 = list.stream().map(s -> {
            s.setSalt("00000000");
            return s;
        }).collect(Collectors.toCollection(ArrayList::new));
        //注意此处省略return必要条件，map操作必须表达式中必须有返回值
        ArrayList<SysUser> peekList2 = list.stream().map(s -> s.setSalt("00000000")).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<SysUser> peekList = list.stream().peek(s -> s.setSalt("00000000")).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(collectToList);
        ArrayList<String> collect1 = peekList.stream().map(SysUser::getUserName).collect(Collectors.toCollection(ArrayList::new));
        String collect2 = collect1.stream().collect(Collectors.joining(" || ", "Start-- ", " --End"));
        System.out.println(collect2);

        //根据流中元素的某个值对流中的变量元素进行分组,并将属性值做为结果map的键
        Map<EnumDel, List<SysUser>> byVariate = list.stream().collect(groupingBy(SysUser::getDelFlag));
        System.out.println(byVariate);
        //根据流中元素判断属性分片
        // 与Boolean元素应用谓语的结果来对项目分区相同
        LocalDateTime endTime = LocalDateTime.of(2020, 1, 1, 0, 0);
        Map<Boolean, List<SysUser>> partitioningByCreateTime =
                list.stream().collect(partitioningBy(s -> s.getCreateTime().isBefore(endTime)));

        //List转Array[Object] 不推荐
        Object[] objects = list.toArray();
        System.out.println("objects----：" + Arrays.toString(objects));
        //List转Array[E] 推荐使用 数组长度可设置为list.size()
        SysUser[] sysUsers = list.toArray(new SysUser[list.size()]);
        System.out.println("sysUsers----：" + Arrays.toString(sysUsers));
        //List中元素转Arrary
        String[] nameArray = list.stream().map(SysUser::getUserName).toArray(String[]::new);
        System.out.println(Arrays.toString(nameArray));
        System.out.println(StringUtils.join(nameArray, ","));
        String[] stringArray = list.stream().map(s -> s.getUserName() + s.getPhone()).toArray(String[]::new);
        //Arrays.asList(sysUsers) 返回一个受指定数组支持的固定大小的列表.所以不能做 Add Remove 等操作
        //运用ArrayList的构造方法是目前来说最完美的作法，代码简洁，效率高：
        ArrayList<SysUser> sysUsers1 = new ArrayList<>(Arrays.asList(sysUsers));

        //1.将数据收集进一个列表(Stream 转换为 List，允许重复值，有顺序)
        //创建流
        Stream<String> strStream = Stream.of("java", "python", "C++", "php", "java");
        List<String> StreamListResult = strStream.collect(Collectors.toList());
        System.out.println(StreamListResult);
        //2.stream()代替流
        List<String> arrList = Arrays.asList("java", "python", "C++", "php", "java");
        //new ArrayList<>(arrList)
        List<String> arrListResult = new ArrayList<>(arrList);
        List<String> arrListResult1 = arrList.stream().collect(Collectors.toList());
        List<String> arrListResult2 = new ArrayList<>(arrList);
        System.out.println(arrListResult);
        // StreamListResult == arrListResult
        //--------------------------
        //1.将数据收集进一个集合(Stream 转换为 Set，不允许重复值，没有顺序)
        Stream<String> strStream2 = Stream.of("java", "python", "C++", "php", "java");
        Set<String> setResult = strStream2.collect(Collectors.toSet());
        System.out.println(setResult);

        //-------------------------
        //用LinkedList收集
        LinkedList<String> arrayListResult1 = arrList.stream().collect(toCollection(LinkedList::new));
        LinkedList<String> arrayListResult = new LinkedList<>(arrList);
        System.out.println(arrayListResult);

        //用CopyOnWriteArrayList收集
        List<String> copyOnWriteArrayListResult1 = new CopyOnWriteArrayList<>(arrList);
        List<String> copyOnWriteArrayListResult = arrList.stream().collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        System.out.println(copyOnWriteArrayListResult);

        //用TreeSet收集
        TreeSet<String> treeSetResult1 = new TreeSet<>(arrList);
        TreeSet<String> treeSetResult = arrList.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(treeSetResult);

        Map<Boolean, List<String>> result = arrList.stream().collect(partitioningBy(s -> s.length() > 2));
        System.out.println(result);

        //收集后转换为不可变List
        ImmutableList<String> ImmutableListCollect = arrList.stream().collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
        System.out.println(ImmutableListCollect);
    }
}
