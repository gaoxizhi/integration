package net.gaox.thread.lock.readWrite.lock;

/**
 * <p> 读写锁内部封装实现类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 17:20
 */
public class ReadWriteLockImpl implements ReadWriteLock {

    /**
     * 定义对象锁
     */
    private final Object MUTEX = new Object();

    /**
     * 当前有多少个线程正在写入
     */
    private int writingWriters = 0;

    /**
     * 当前有多少个线程正在等待写入
     */
    private int waitingWriters = 0;

    /**
     * 当前有多少个线程正在read
     */
    private int readingReaders = 0;

    /**
     * 读写的偏好设置，一般来说读写锁非常适用于读多写少的场景
     * 如果设置为 false，很多读线程都在读数据，那么写线程将会很难得到写的机会
     */
    private boolean preferWriter;

    /**
     * 默认情况下 preferWriter 为true
     */
    public ReadWriteLockImpl() {
        this(true);
    }

    /**
     * 构造 ReadWriteLockImpl 并且传入 preferWriter
     *
     * @param preferWriter prefer
     */
    public ReadWriteLockImpl(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    /**
     * 创建 read lock
     *
     * @return 读锁
     */
    public Lock readLock() {
        return new ReadLock(this);
    }

    /**
     * 创建 write lock
     *
     * @return 写锁
     */
    public Lock writeLock() {
        return new WriteLock(this);
    }

    /**
     * 写线程的数量增加
     */
    void incrementWritingWriters() {
        this.writingWriters++;
    }

    /**
     * 等待写入的线程数量增加
     */
    void incrementWaitingWriters() {
        this.waitingWriters++;
    }

    /**
     * 读线程的数量增加
     */
    void incrementReadingReaders() {
        this.readingReaders++;
    }

    /**
     * 写线程的数量减少
     */
    void decrementWritingWriters() {
        this.writingWriters--;
    }

    /**
     * 等待获取写入锁的数量减一
     */
    void decrementWaitingWriters() {
        this.waitingWriters--;
    }

    /**
     * 读取线程的数量减少
     */
    void decrementReadingReaders() {
        this.readingReaders--;
    }

    /**
     * 获取当前有多少个线程正在进行写操作
     *
     * @return 写操作的线程数
     */
    public int getWritingWriters() {
        return this.writingWriters;
    }

    /**
     * 获取当前有多少个线程正在等待获取写入锁
     *
     * @return 等待写操作的线程数
     */
    public int getWaitingWriters() {
        return this.waitingWriters;
    }

    /**
     * 获取当前多少个线程正在进行读操作
     *
     * @return 读操作的线程数
     */
    public int getReadingReaders() {
        return this.readingReaders;
    }

    /**
     * 获取对象锁
     *
     * @return 对象锁
     */
    Object getMutex() {
        return this.MUTEX;
    }

    /**
     * 获取读写偏好
     *
     * @return 读写偏好
     */
    boolean getPreferWriter() {
        return this.preferWriter;
    }

    /**
     * 获取读写偏好
     */
    void changePrefer(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

}
