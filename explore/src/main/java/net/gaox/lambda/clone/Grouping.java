package net.gaox.lambda.clone;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/11/15 11:03
 */
public class Grouping {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //为集合初始化多条数据，根据id 合并inCome字段使用
        List<People> list = Arrays.asList(
                People.builder().id(101).name("张三").inCome(5000).build(),
                People.builder().id(102).name("李四").inCome(8000).build(),
                People.builder().id(103).name("王五").inCome(3000).build(),
                People.builder().id(102).name("李四").inCome(2000).build(),
                People.builder().id(103).name("王五").inCome(4500).build()
        );
        final List<People> people3 = deepCopy(list);

        //map分组实现

        final Map<Integer, List<People>> collect = list.stream().collect(Collectors.groupingBy(People::getId));
        List<People> people = new ArrayList<>();
        for (Integer id : collect.keySet()) {
            people.add(collect.get(id).get(0).setInCome(collect.get(id).stream().map(People::getInCome).reduce(0D, Double::sum)));
        }
        people.forEach(System.out::println);

        List<People> people2 = list.stream().map(s -> new People(s.getId(), s.getName(), s.getInCome())).collect(Collectors.toList());
        List<People> collectList2 = list.stream().peek(s -> s.setInCome(people2.stream().filter(i -> s.getId() == i.getId()).map(People::getInCome).reduce(0D, Double::sum))).distinct().collect(Collectors.toList());
        collectList2.forEach(System.out::println);

        List<People> collectList = list.stream().peek(s ->
                s.setInCome(people3.stream().filter(i ->
                        s.getId() == i.getId()).map(People::getInCome).reduce(0D, Double::sum)
                )
        ).distinct().collect(Collectors.toList());
        collectList.forEach(System.out::println);
    }

    /**
     * 利用字节数组（ByteArray）输出（输入流）序列化实现深拷贝List
     * 字节数组输出（输入）流则是将其保存在一个字节数组的临时变量中，仅占用内存空间，用后会自动清除。
     *
     * @param src 源数组
     * @param <T> 范型
     * @return 范型的list
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类型找不到
     */
    private static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(src);

        final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        final ObjectInputStream ois = new ObjectInputStream(bis);
        @SuppressWarnings(("unchecked"))
        List<T> desc = (List<T>) ois.readObject();
        return desc;
    }

}
