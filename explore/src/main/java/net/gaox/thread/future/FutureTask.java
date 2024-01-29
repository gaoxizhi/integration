package net.gaox.thread.future;

/**
 * <p> Future 任务调度器 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 09:39
 */
public class FutureTask<T> implements Future<T> {

    /**
     * 计算结果
     */
    private T result;

    /**
     * 任务是否完成
     */
    private boolean isDone = false;

    /**
     * 定义对象锁
     */
    private final Object LOCK = new Object();

    @Override
    public T get() throws InterruptedException {
        synchronized (LOCK) {
            // 当任务还没完成时，调用 get 方法会被挂起而进入阻塞
            while (!isDone) {
                LOCK.wait();
            }
            // 返回最终计算结果
            return result;
        }
    }

    /**
     * finish 方法主要用于为 FutureTask 设置计算结果
     *
     * @param result 计算结果
     */
    protected void finish(T result) {
        synchronized (LOCK) {
            // balking 设计模式
            if (isDone)
                return;
            // 计算完成，为 result 指定结果，并且将 isDone 设为 true，同时唤醒阻塞中的线程
            this.result = result;
            this.isDone = true;
            LOCK.notifyAll();
        }
    }

    /**
     * 返回当前任务是否已经完成
     *
     * @return 任务是否完成
     */
    @Override
    public boolean done() {
        return isDone;
    }

}
