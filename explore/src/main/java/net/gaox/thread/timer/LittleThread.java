package net.gaox.thread.timer;

/**
 * <p> 线程等待唤醒 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:12
 */
public class LittleThread {
    private Object lock = new Object();

    public void waitLock() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }

    public void notifyLock() {
        synchronized (lock) {
            lock.notify();
        }
    }

}
