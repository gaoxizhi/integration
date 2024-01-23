package net.gaox.thread.gaoxpool.factory;

/**
 * <p> 线程工厂 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:21
 */
public interface ThreadFactory {

    Thread createThread(Runnable runnable);

}
