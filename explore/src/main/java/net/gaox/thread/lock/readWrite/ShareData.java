package net.gaox.thread.lock.readWrite;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.lock.readWrite.lock.Lock;
import net.gaox.thread.lock.readWrite.lock.ReadWriteLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p> 读写锁的共享数据 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-28 18:20
 */
@Slf4j
public class ShareData {

    /**
     * 共享数据的长度
     */
    private final int length;

    /**
     * 共享数据载体
     */
    private final List<Character> container = new ArrayList<>();

    /**
     * 首先，创建一个 ReadWriteLock 工厂类
     */
    private final ReadWriteLock readWriteLock = ReadWriteLock.readWriteLock();

    /**
     * 用该工厂创建 ReadLock 的实例
     */
    private final Lock readLock = readWriteLock.readLock();

    /**
     * 用该工厂创建 WriteLock 的实例
     */
    private final Lock writeLock = readWriteLock.writeLock();

    /**
     * 构造函数，构造一个指定长度的共享数据
     *
     * @param length 共享数据的长度
     */
    public ShareData(int length) {
        this.length = length;
        for (int i = 0; i < length; i++) {
            container.add(i, 'c');
        }
    }

    /**
     * 从共享数据中读取数据
     *
     * @return 读取到的数据
     * @throws InterruptedException 中断异常
     */
    public char[] read() throws InterruptedException {
        try {
            // 首先使用读锁进行 lock
            readLock.lock();
            char[] newBuffer = new char[length];
            for (int i = 0; i < length; i++) {
                newBuffer[i] = container.get(i);
            }
            slowly();
            return newBuffer;
        } finally {
            // 当操作结束之后，将锁释放
            readLock.unlock();
        }
    }

    /**
     * 将一个字符写入到共享数据中
     *
     * @param c 字符
     * @throws InterruptedException 中断异常
     */
    public void write(char c) throws InterruptedException {
        try {
            // 使用写锁进行lock
            writeLock.lock();
            for (int i = 0; i < length; i++) {
                this.container.add(i, c);
            }
            slowly();
        } finally {
            // 当所有的操作都完成之后，对写锁进行释放
            writeLock.unlock();
        }
    }

    /**
     * 简单模拟操作的耗时
     */
    private void slowly() {
        try {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            TimeUnit.MILLISECONDS.sleep(current.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
