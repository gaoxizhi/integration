package net.gaox.thread.gaoxpool.factory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 线程默认工厂方法 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 14:36
 */
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);

    private static final ThreadGroup group = new ThreadGroup("MyThreadPool-" + GROUP_COUNTER.getAndDecrement());

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @Override
    public Thread createThread(Runnable runnable) {
        return new Thread(group, runnable, "thread-pool-" + COUNTER.getAndDecrement());
    }
}

