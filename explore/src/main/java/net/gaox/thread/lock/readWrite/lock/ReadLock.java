package net.gaox.thread.lock.readWrite.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 读锁 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 18:00
 */
@Slf4j
public class ReadLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    /**
     * 获取读锁
     * 当没有任何线程对数据进行写操作的时候，读线程才有可能获得锁的拥有权
     * 当然除此之外，为了公平起见，如果当前有很多线程正在等待获得写锁的拥有权
     * 同样读线程将会进入 Mutex 的 wait set 中，readingReader 的数量将增加
     *
     * @throws InterruptedException 中断异常
     */
    @Override
    public void lock() throws InterruptedException {
        // 使用 Mutex 作为锁
        synchronized (readWriteLock.getMutex()) {
            // log.debug("尝试获取读锁.");
            // 若此时有线程在进行写操作，或者有写线程在等待并且偏向写锁的标识为true时，就会无法获得读锁，只能被挂起
            while (readWriteLock.getWritingWriters() > 0 ||
                    (readWriteLock.getPreferWriter() && readWriteLock.getWaitingWriters() > 0)) {
                readWriteLock.getMutex().wait();
            }
            // 成功获得读锁，并且使 readingReaders 的数量增加
            readWriteLock.incrementReadingReaders();
            // log.debug("获得读锁.");
        }
    }

    /**
     * 释放读锁
     * reader 的数量将减少一个，同时唤醒 wait 中的线程，reader 唤醒的基本上都是由于获取写锁而进入阻塞的线程
     * 为了提高写锁获得锁的机会，需要将 preferWriter 修改为 true
     */
    @Override
    public void unlock() {
        // 使用 Mutex 作为锁，并且进行同步
        synchronized (readWriteLock.getMutex()) {
            // log.debug("尝试释放读锁.");
            // 释放锁的过程就是使得当前 reading 的数量减一
            readWriteLock.decrementReadingReaders();
            // 将 preferWriter 设置为 true，可以使得 writer线程获得更多的机会
            readWriteLock.changePrefer(true);
            // 通知唤醒与 Mutex 关联 monitor wait set 中的线程
            readWriteLock.getMutex().notifyAll();
            // log.debug("释放读锁.");
        }
    }
}
