package net.gaox.thread.alternating;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p> 顺序执行条件 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 17:22
 */
@Slf4j
public class AlternateCondition {

    /**
     * 当前正在执行线程的标记
     */
    private int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     * @param totalLoop : 循环第几轮
     */
    public void loopA(int totalLoop) {
        lock.lock();
        try {
            //1. 判断，否则等待
            if (number != 1) {
                condition1.await();
            }
            //2. 工作
            log.info("totalLoop = {}", totalLoop);
            //3. 唤醒
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) {
        lock.lock();
        try {
            //1. 判断
            if (number != 2) {
                condition2.await();
            }
            //2. 工作
            log.info("totalLoop = {}", totalLoop);
            //3. 唤醒
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {
        lock.lock();
        try {
            //1. 判断
            if (number != 3) {
                condition3.await();
            }
            //2. 工作
            log.info("totalLoop = {}", totalLoop);
            //3. 唤醒
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
