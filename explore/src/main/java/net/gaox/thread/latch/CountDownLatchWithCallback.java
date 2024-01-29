package net.gaox.thread.latch;

import java.util.concurrent.TimeUnit;

/**
 * <p> 带回调功能的阀门接口实现 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 13:39
 */
public class CountDownLatchWithCallback extends Latch {
    private final Runnable runnable;

    public CountDownLatchWithCallback(int limit) {
        this(limit, null);
    }

    public CountDownLatchWithCallback(int limit, Runnable runnable) {
        super(limit);
        this.runnable = runnable;
    }

    @Override
    public void await() throws InterruptedException {
        synchronized (this) {
            // 当 limit>0 时，当前线程进入阻塞状态
            while (limit > 0) {
                this.wait();
            }
        }
        // 执行回调方法
        if (null != runnable) {
            runnable.run();
        }
    }

    @Override
    public void await(TimeUnit unit, long time) throws InterruptedException, WaitTimeoutException {
        if (time <= 0)
            throw new IllegalArgumentException("The time is invalid.");

        // 将 time 转换为纳秒
        long remainingNanos = unit.toNanos(time);
        // 等待任务将在 endNanos 纳秒后超时
        final long endNanos = System.nanoTime() + remainingNanos;
        synchronized (this) {
            while (limit > 0) {
                // 如果超时则抛出 WaitTimeoutException 异常
                if (TimeUnit.NANOSECONDS.toMillis(remainingNanos) <= 0)
                    throw new WaitTimeoutException("The wait time over specify time.");
                // 等待 remainingNanos，在等待的过程中有可能会被中断，需要重新计算 remainingNanos
                this.wait(TimeUnit.NANOSECONDS.toMillis(remainingNanos));
                // 重置超时时间
                remainingNanos = endNanos - System.nanoTime();
            }
        }

        if (null != runnable) {
            runnable.run();
        }
    }

    @Override
    public void countDown() {
        synchronized (this) {
            if (limit < 0)
                throw new IllegalStateException("all of task already arrived");
            limit--;
            this.notifyAll();
        }
    }

    @Override
    public int getUnArrived() {
        return limit;
    }

}
