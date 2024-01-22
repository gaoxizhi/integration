package net.gaox.thread.lock;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * <p> 锁接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-22 14:26
 */
public interface Lock {

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeoutException;

    void unlock();

    List<Thread> getBlockedThreads();

}
