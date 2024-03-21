package net.gaox.thread.lock.readWrite.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p> 读写锁示例 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 18:05
 */
@Slf4j
public class ReadWriteLocks {

    private int number = 0;

    /**
     * 读写锁
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 获取数据
     */
    @SneakyThrows
    public void get() {
        // 读上锁
        lock.readLock().lock();
        try {
            log.info("read  number: {}", number);
            TimeUnit.MILLISECONDS.sleep(1);
        } finally {
            // 释放读锁
            lock.readLock().unlock();
        }
    }

    /**
     * 设置数据
     *
     * @param number 数值
     */
    @SneakyThrows
    public void set(int number) {
        // 写上锁
        lock.writeLock().lock();
        try {
            log.info("write number: {}", number);
            TimeUnit.MILLISECONDS.sleep(5);
            this.number = number;
        } finally {
            // 释放写锁
            lock.writeLock().unlock();
        }
    }

}