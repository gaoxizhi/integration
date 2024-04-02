package net.gaox.thread.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * <p> ForkJoin 递归数值累加 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 18:36
 */
@Slf4j
public class ForkJoinInvokeSumCalculate extends RecursiveTask<Long> {

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


    public ForkJoinInvokeSumCalculate(long start, long end, long threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        log.debug("执行计算：[{}]~[{}]", start, end);
        if (length <= threshold) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            log.debug("开始执行：[{}]~[{}]", start, end);
            return sum;
        } else {
            // 计算中间值，分两次递归求解
            long middle = (start + end) / 2;
            log.debug("进行递归执行：[{}]~[{}]", start, end);

            // 进行拆分，同时压入线程队列
            ForkJoinInvokeSumCalculate left = new ForkJoinInvokeSumCalculate(start, middle, threshold);
            ForkJoinInvokeSumCalculate right = new ForkJoinInvokeSumCalculate(middle + 1, end, threshold);
            invokeAll(left, right);

            return left.join() + right.join();
        }
    }

}

