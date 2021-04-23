package net.gaox.base.statistics;

import java.util.*;

/**
 * @Description: <p>  </p>
 * @ClassName CharStatistics
 * @Author: gaox·Eric
 * @Date: 2019/3/31 16:34
 */
public class CharStatistics {

    public static void main(String[] args) {

        //new 一个HashMap键为字符，键值为统计的数字
        HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
        System.out.print("请输入一句话：");
        Scanner sc = new Scanner(System.in);
        String words = sc.nextLine();
        //把这句话中的所有字符的个数可以存储起来
        for (int i = 0; i < words.length(); i++) {
            char c = words.charAt(i);
            //如果在map中没有这个字符，添加一个键值对，键为该字符键值为1
            if (!hm.containsKey(c)) {
                hm.put(c, 1);
            } else {
                //如果查到该字符已经存在，取出键值并+1再存入
                hm.put(c, hm.get(c) + 1);
            }
        }
        //将所有键值存储到集合count
        Collection<Integer> count = hm.values();
        //找出次数最多那个数
        int maxCount = Collections.max(count);
        //根据最大值去找它对应的key值--字符
        System.out.println("出现次数最多的字符是：");

        Set<Map.Entry<Character, Integer>> set = hm.entrySet();
        for (Map.Entry<Character, Integer> i : set) {
            if (i.getValue() == maxCount) {
                System.out.println(i.getKey() + "的次数是：" + maxCount);
            }
        }
    }
}
