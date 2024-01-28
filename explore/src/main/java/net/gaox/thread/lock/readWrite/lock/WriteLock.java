package net.gaox.thread.lock.readWrite.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 写锁 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 18:01
 */
@Slf4j
public class WriteLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    WriteLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    /**
     * 获取写锁
     * 当有线程在进行读操作或者写操作的时候，若当前线程试图获得锁
     * 则其将会进入 MUTEX 的 wait set 中而阻塞，同时增加 waitingWriter 和 writingWriter 的数量
     * 但是当线程从 wait set 中被激活的时候 waitingWriter 将很快被减少
     *
     * @throws InterruptedException 中断异常
     */
    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMutex()) {
            try {
                // log.debug("尝试获取写锁.");
                // 首先使等待获取写入锁的数字加一
                readWriteLock.incrementWaitingWriters();
                // 如果此时有其他线程正在进行 读写操作，那么当前线程将被挂起
                while (readWriteLock.getReadingReaders() > 0 || readWriteLock.getWritingWriters() > 0) {
                    readWriteLock.getMutex().wait();
                }
            } finally {
                // 无论成功获取到了写入锁与否，使得等待获取写入锁的计数器减一
                this.readWriteLock.decrementWaitingWriters();
            }
            // 将正在写入的线程数量加一
            readWriteLock.incrementWritingWriters();
            // log.debug("获得写锁.");
        }
    }

    /**
     * 释放写锁
     * 意味着 writer 的数量减少，事实上变成了0
     * 同时唤醒 wait 中的线程，并将 preferWriter 修改为 false，以提高读线程获得锁的机会
     */
    @Override
    public void unlock() {
        synchronized (readWriteLock.getMutex()) {
            // log.debug("尝试释放写锁.");
            // 减少正在写入锁的线程计数器
            readWriteLock.decrementWritingWriters();
            // 将偏好状态修改为 false，可以使得读锁被最快速的获得
            readWriteLock.changePrefer(false);
            // 通知唤醒其他在 Mutex monitor wait set 中的线程
            readWriteLock.getMutex().notifyAll();
            // log.debug("释放写锁.");
        }
    }

}
