package net.gaox.thread.gaoxpool;

/**
 * <p> 线程池接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:11
 */
public interface ThreadPool {

    void execute(Runnable runnable);

    void shutdown();

    int getInitSize();

    int getMaxSize();

    int getCoreSize();

    int getQueueSize();

    int getActiveCount();

    boolean isShutdown();

}
