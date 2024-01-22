package net.gaox.thread.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;

/**
 * <p> 显式的 BooleanLock </p>
 * 具备 synchronized 关键字所有功能，同时又具备可中断和 lock 超时的功能
 *
 * @author gaox·Eric
 * @date 2024-01-22 14:30
 */
@Slf4j
public class BooleanLock implements Lock {

    private Thread currentThread;

    private boolean locked = false;

    private final List<Thread> blockedList = new ArrayList<>();

    /**
     * 加锁
     * 方法永远阻塞，除非获取到了锁，这一点和 synchronized 非常类似
     * 但是该方法是可以被中断的，中断时会抛出InterruptedException异常。
     *
     * @throws InterruptedException
     */
    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {

            // 如果当前锁已经被某个线程获得
            while (locked) {
                //暂存当前线程
                final Thread tempThread = currentThread();
                try {
                    // 把该线程将加入阻塞队列
                    if (!blockedList.contains(tempThread)) {
                        blockedList.add(tempThread);
                    }
                    // 使当前线程 wait 释放对 this monitor 的所有权
                    this.wait();
                } catch (InterruptedException e) {
                    //如果当前线程在 wait 时被中断，则从 blockedList 中将其删除，避免内存泄漏
                    blockedList.remove(tempThread);
                    //继续抛出中断异常
                    throw e;
                }
            }

            // 如果当前锁没有被其他线程获得，则该线程将尝试从阻塞队列中删除自己
            // -> 如果当前线程从未进入过阻塞队列，删除方法不会有任何影响
            // -> 如果当前线程是从 wait set 中被唤醒的，则需要从阻塞队列中将自己删除
            blockedList.remove(currentThread());
            // locked开关被指定为true
            this.locked = true;
            // 记录获取锁的线程
            this.currentThread = currentThread();
        }
    }

    /**
     * 增加了对应的超时功能的加锁方式
     *
     * @param mills 超时时间
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {
            // mills 不合法或 0，则默认调用lock（）方法
            if (mills <= 0) {
                this.lock();
            } else {
                long remainingMills = mills;
                long intoTimeMillis = currentTimeMillis();
                long endMills = intoTimeMillis + remainingMills;
                while (locked) {
                    log.trace("设置的超时 = {} ms, 当前时间 = {}, 超时时间 = {}", remainingMills, intoTimeMillis, endMills);
                    // 如果 remainingMills 小于等于 0，会抛出超时的异常
                    // 则意味着当前线程被其他线程唤醒或者在指定的 wait 时间到了之后还没有获得锁
                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the lock during " + mills + " ms.");
                    }
                    if (!blockedList.contains(currentThread())) {
                        blockedList.add(currentThread());
                    }
                    // 等待 remainingMills 的毫秒数，该值最开始是由其他线程传入的，但在多次 wait 的过程中会重新计算
                    this.wait(remainingMills);
                    // 重新计算remainingMills时间
                    remainingMills = endMills - currentTimeMillis();
                }
                // 获得该锁，并且从 block 列表中删除当前线程，将 locked 的状态修改为 true 并且指定获得锁的线程就是当前线程
                blockedList.remove(currentThread());
                this.locked = true;
                this.currentThread = currentThread();
            }
        }
    }

    /**
     * 用来进行锁的释放
     * 将locked状态修改为 false，并且唤醒 wait set 中的其他线程，再次争抢锁资源
     */
    @Override
    public void unlock() {
        synchronized (this) {
            // 判断当前线程是否为获取锁的那个线程，只有加了锁的线程才有资格进行解锁
            if (currentThread == currentThread()) {
                // 将锁的 locked 状态修改为 false
                this.locked = false;
                Optional.of(currentThread().getName() + " release the lock monitor.").ifPresent(System.out::println);
                log.debug("thread [{}] release the lock monitor.", currentThread().getName());
                // 通知其他在 wait set 中的线程，可以再次尝试抢锁，这里使用 notify 和 notifyAll 都可以
                this.notifyAll();
            }
        }
    }

    /**
     * 获取当前有哪些线程被阻塞
     *
     * @return 被阻塞的线程列表
     */
    @Override
    public List<Thread> getBlockedThreads() {
        return Collections.unmodifiableList(blockedList);
    }

}
