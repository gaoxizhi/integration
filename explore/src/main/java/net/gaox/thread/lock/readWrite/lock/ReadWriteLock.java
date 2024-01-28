package net.gaox.thread.lock.readWrite.lock;

/**
 * <p> 读写锁接口工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 17:10
 */
public interface ReadWriteLock {

    /**
     * 创建 reader 锁
     *
     * @return reader 锁
     */
    Lock readLock();

    /**
     * 创建 write 锁
     *
     * @return write 锁
     */
    Lock writeLock();

    /**
     * 获取当前有多少线程正在执行写操作
     *
     * @return 执行写操作的 线程数
     */
    int getWritingWriters();

    /**
     * 获取当前有多少线程正在等待获取写入锁
     *
     * @return 等待获取写入锁的 线程数
     */
    int getWaitingWriters();

    /**
     * 获取当前有多少线程正在等待获取reader锁
     *
     * @return 等待获取reader锁的 线程数
     */
    int getReadingReaders();

    /**
     * 工厂方法，创建 ReadWriteLock
     *
     * @return 读写锁实现
     */
    static ReadWriteLock readWriteLock() {
        return new ReadWriteLockImpl();
    }

    /**
     * 工厂方法，创建 ReadWriteLock，并且传入 preferWriter
     *
     * @param preferWriter 读写的偏好设置
     * @return 读写锁实现
     */
    static ReadWriteLock readWriteLock(boolean preferWriter) {
        return new ReadWriteLockImpl(preferWriter);
    }

}
