package net.gaox.thread.latch;

import java.util.concurrent.TimeUnit;

/**
 * <p> 阀门接口，用于控制线程的同步 </p>
 * 等待所有子任务完成后再执行其他任务
 *
 * @author gaox·Eric
 * @date 2024-01-29 13:23
 */
public abstract class Latch {

    /**
     * 用于控制多少个线程完成任务时才能打开阀门
     */
    protected int limit;

    /**
     * 通过构造函数传入 limit
     *
     * @param limit 阀门值
     */
    public Latch(int limit) {
        this.limit = limit;
    }

    /**
     * 让当前线程一直等待
     * 用于等待所有的子任务完成，如果到达数量未达到 limit 的时候，将会无限等待下去
     *
     * @throws InterruptedException 中断异常
     */
    public abstract void await() throws InterruptedException;

    /**
     * 让当前线程一直等待，设定超时时间
     *
     * @param unit 时间单位
     * @param time 时长
     * @throws InterruptedException 中断异常
     * @throws WaitTimeoutException 等待超时异常
     */
    public abstract void await(TimeUnit unit, long time) throws InterruptedException, WaitTimeoutException;

    /**
     * 当任务线程完成工作之后调用该方法使得计数器减一
     */
    public abstract void countDown();

    /**
     * 获取当前还有多少个线程没有完成任务,返回值并不一定就是准确的是一个评估值
     *
     * @return 没有完成任务的线程数
     */
    public abstract int getUnArrived();

}
