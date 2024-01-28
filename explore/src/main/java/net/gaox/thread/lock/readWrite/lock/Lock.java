package net.gaox.thread.lock.readWrite.lock;

/**
 * <p> 锁接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 16:59
 */
public interface Lock {

    /**
     * 加锁，当前线程尝试获得锁的拥有权，在此期间有可能进入阻塞
     *
     * @throws InterruptedException e
     */
    void lock() throws InterruptedException;

    /**
     * 释放锁，其主要目的就是为了减少 reader 或者 writer 的数量
     */
    void unlock();

}
