package net.gaox.util.list;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p> list 通过 id 合并 </p>
 *
 * @author gaox·Eric
 * @date 2024-07-04 13:50
 */
@Slf4j
public class ListMerger {
    public static void main(String[] args) {
        List<Item> list1 = new ArrayList<>();
        list1.add(new Item(51));
        list1.add(new Item(22));
        list1.add(new Item(63));

        List<Item> list2 = new ArrayList<>();
        list2.add(new Item(11));
        // 这个ID在list1中已经存在，所以会被替换
        list2.add(new Item(22));
        list2.add(new Item(5));

        List<Item> mergedList = mergeLists(list1, list2);
        log.info("list1 = {}", list1.stream().map(Item::getId).collect(Collectors.toList()));
        log.info("list2 = {}", list2.stream().map(Item::getId).collect(Collectors.toList()));
        log.info("mergedList = {}", mergedList.stream().map(Item::getId).collect(Collectors.toList()));
    }

    /**
     * 合并两个列表。此方法会保留list1中的所有元素，并根据ID更新或添加list2中的元素。
     * 假设Item类具有一个不为空的getId()方法用于唯一标识每个元素。
     *
     * @param list1 第一个列表，其元素将被复制到结果列表中并可能被list2中的元素更新。
     * @param list2 第二个列表，其元素将被用于更新或添加到结果列表中。
     * @return 一个新列表，包含合并后的元素。
     */
    public static List<Item> mergeLists(List<Item> list1, List<Item> list2) {
        List<Item> orderedList = new ArrayList<>(list1);

        // idIndexMap
        HashMap<Integer, Integer> idIndexMap = IntStream.range(0, list1.size()).boxed()
                .collect(Collectors.toMap(i -> list1.get(i).getId(), i -> i, (a, b) -> a, HashMap::new));
        list2.forEach(s -> {
            if (idIndexMap.containsKey(s.getId())) {
                orderedList.set(idIndexMap.get(s.getId()), s);
            } else {
                orderedList.add(s);
            }
        });
        return orderedList;
    }
}
