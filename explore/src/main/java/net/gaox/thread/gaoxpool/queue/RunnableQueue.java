package net.gaox.thread.gaoxpool.queue;

/**
 * <p> 运行队列 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:20
 */
public interface RunnableQueue {

    void offer(Runnable runnable);

    Runnable take() throws InterruptedException;

    int size();

}
