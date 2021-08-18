package net.gaox.lambda;

import net.gaox.entity.People;
import net.gaox.util.CloneUtils;

import java.io.IOException;
import java.util.ArrayList;
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
        List<People> list = People.list;
        final List<People> people3 = CloneUtils.deepCopy(list);

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

}
