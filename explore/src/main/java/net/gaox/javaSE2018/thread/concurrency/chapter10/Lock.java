package net.gaox.javaSE2018.thread.concurrency.chapter10;

import java.util.Collection;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public interface Lock {

    class TimeOutException extends Exception {

        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();

}