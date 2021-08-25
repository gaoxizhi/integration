package net.gaox.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * <p> 集合工具类 </p>
 *
 * @author gaox·Eric
 * @date 2021/8/26 00:12
 */
public class CollectionUtil {

    /**
     * 获取集合重复出现的元素
     *
     * @param collection 集合
     * @param <T>        范型
     * @return 重复元素list
     */
    public static <T> List<T> getDuplicateElements(Collection<T> collection) {
        return collection.stream()
                // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b))
                // Set<Entry>转换为Stream<Entry>
                .entrySet().stream()
                // 过滤出元素出现次数大于 1 的 entry
                .filter(entry -> entry.getValue() > 1)
                // 获得 entry 的键（重复元素）对应的 Stream
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
    }

    /**
     * 获取集合重复出现的元素 - 以Set方式实现
     *
     * @param collection 集合
     * @param <T>        范型
     * @return 重复元素list
     */
    public static <T> List<T> getDuplicateElementsBySet(Collection<T> collection) {
        // 深拷贝原集合
        List<T> list = null;
        try {
            list = CloneUtils.deepCopy(collection.stream().collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 拷贝失败或者原集合为null
        if (null == list) {
            return null;
        }

        // 获取原集合的Set
        TreeSet<T> nameSet = new TreeSet<T>(list);
        // 剩下的都是重复的（逻辑：list - set）
        for (T s : nameSet) {
            list.remove(s);
        }

        // 最终list去重便是重复出现的元素
        List<T> collect = list.stream().distinct().collect(Collectors.toList());
        return collect;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // 获取集合内的重复元素
        List<String> nameList = Arrays.asList("gaox", "gaoxizhi", "gaox", "gao", "gaoxizhi", "fashi", "master", "supper", "gaox");
        System.out.printf("nameList before processing: %s\n", nameList);
        List<String> duplicateElements = getDuplicateElements(nameList);
        System.out.printf("getDuplicateElements: %s\n", duplicateElements);
        List<String> duplicateElementsBySet = getDuplicateElementsBySet(nameList);
        System.out.printf("getDuplicateElementsBySet: %s\n", duplicateElementsBySet);
    }
}
