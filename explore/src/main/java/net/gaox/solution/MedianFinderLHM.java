package net.gaox.solution;

/**
 * <p> 使用 LinkedHashMap 查找中位数 </p>
 * 每次重新计算中位数位置
 *
 * @author gaox·Eric
 * @date 2024-03-23 13:50
 */

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

public class MedianFinderLHM {
    private LinkedHashMap<Integer, Integer> map;
    private int count;

    @Getter
    private Double median;

    public MedianFinderLHM() {
        map = new LinkedHashMap<>();
        count = 0;
        median = 0D;
    }

    public void addNumber(int num) {
        map.put(num, map.getOrDefault(num, 0) + 1);
        count++;
        recalculateMedian();
    }

    private void recalculateMedian() {
        int middleIndex = count / 2;
        int currentIndex = 0;
        int prevNum = -1;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int currentNum = entry.getKey();
            int occurrences = entry.getValue();

            for (int i = 0; i < occurrences; i++) {
                currentIndex++;

                if (currentIndex == middleIndex) {
                    if (count % 2 == 1) {
                        // 奇数个元素，中位数就是当前元素
                        median = (double) currentNum;
                    } else if (currentIndex == middleIndex + 1) {
                        // 偶数个元素，中位数是prevNum和currentNum的平均值
                        median = (prevNum + currentNum) / 2.0;
                    }
                    break;
                }

                prevNum = currentNum;
            }
        }
    }

}