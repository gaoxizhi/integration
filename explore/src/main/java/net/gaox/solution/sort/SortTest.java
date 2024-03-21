package net.gaox.solution.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * <p> 排序算法测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-18 11:00
 */
public class SortTest {
    static int[] array = {12, 989, 45, 1, 63, 79, 36, 98, 6, 73, 36, 69, 16};

    @Test
    public void bubbleSort() {
        BubbleSort.bubbleSort(Arrays.stream(array).toArray());
    }

    @Test
    public void insertSort() {
        InsertSort.insertSort(Arrays.stream(array).toArray());
    }

    @Test
    public void insertSort2() {
        InsertSort.insertSort2(Arrays.stream(array).toArray());
    }

    @Test
    public void chooseSort() {
        ChooseSort.chooseSort(Arrays.stream(array).toArray());
    }

    @Test
    public void shellSort() {
        ShellSort.shellSort(Arrays.stream(array).toArray());
    }

    @Test
    public void heapSort() {
        HeapSort.heapSort(Arrays.stream(array).toArray());
    }

    @Test
    public void mergeSort() {
        MergeSort.mergeSort(Arrays.stream(array).toArray());
    }

    @Test
    public void monkeySort() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> MonkeySort.monkeySort(Arrays.stream(array).toArray()));
        try {
            // 设置10秒超时
            future.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("排序过程被中断");
        } catch (ExecutionException e) {
            System.err.println("排序过程中发生错误: " + e.getCause());
        } catch (TimeoutException e) {
            // 超时后取消任务
            future.cancel(true);
            System.err.println("排序过程超过10秒，已取消");
        } finally {
            // 关闭线程池
            executor.shutdownNow();
        }
    }

}
