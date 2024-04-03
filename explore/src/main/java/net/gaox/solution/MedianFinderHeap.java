package net.gaox.solution;

/**
 * <p> 使用PriorityQueue 优先队列实现的堆排序 查找中位数 </p>
 * 大根堆倒序，小根堆正序
 * 大根堆把小值给小根堆作为大值
 *
 * @author gaox·Eric
 * @date 2024-03-23 14:10
 */

import java.util.PriorityQueue;

public class MedianFinderHeap {

    /**
     * 存储较小一半元素的大根堆
     */
    private PriorityQueue<Integer> maxHeap;

    /**
     * 存储较大一半元素的小根堆
     */
    private PriorityQueue<Integer> minHeap;

    public MedianFinderHeap() {
        // 初始化大根堆
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        // 初始化小根堆
        minHeap = new PriorityQueue<>();
    }

    public void addNumber(int num) {
        // 将新元素添加到maxHeap
        // 此时堆顶是最小值
        maxHeap.offer(num);

        // 保持两个堆的元素数量差最多为1
        if (maxHeap.size() > minHeap.size() + 1) {
            // 移除maxHeap顶部元素并添加到minHeap
            minHeap.offer(-maxHeap.poll());
        } else if (maxHeap.size() < minHeap.size()) {
            // 移除minHeap顶部元素并添加到maxHeap
            maxHeap.offer(-minHeap.poll());
        }
    }

    public double getMedian() {
        if (maxHeap.size() == minHeap.size()) {
            // 偶数个元素时的中位数
            return ((double) maxHeap.peek() - minHeap.peek()) / 2.0;
        } else {
            // 奇数个元素时的中位数
            return maxHeap.peek();
        }
    }

}
