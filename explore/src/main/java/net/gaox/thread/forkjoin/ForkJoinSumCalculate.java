package net.gaox.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * <p> ForkJoin 递归数值累加 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 18:36
 */
public class ForkJoinSumCalculate extends RecursiveTask<Long> {

    /**
     * 最小值
     */
    private long start;

    /**
     * 最大值
     */
    private long end;

    /**
     * 单次计算阈值
     */
    private long threshold;


    public ForkJoinSumCalculate(long start, long end, long threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length <= threshold) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 计算中间值，分两次递归求解
            long middle = (start + end) / 2;

            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle, threshold);
            // 进行拆分，同时压入线程队列
            left.fork();
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end, threshold);
            right.fork();

            return left.join() + right.join();
        }
    }

}

