package net.gaox.thread.lock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;


/**
 * <p> 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-22 15:00
 */
public class BooleanLockTest {

    private final Lock lock = new BooleanLock();

    public static void main(String[] args) throws InterruptedException {
        BooleanLockTest blt = new BooleanLockTest();


        new Thread(blt::syncMethod, "T1").start();
        TimeUnit.MILLISECONDS.sleep(2);

        Thread t2 = new Thread(blt::syncMethodTimeoutAble, "T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);

        IntStream.range(0, 10).mapToObj(i -> new Thread(blt::syncMethod)).forEach(Thread::start);
    }

    public void syncMethod() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread() + " get the lock.");
            int randomInt = ThreadLocalRandom.current().nextInt(1000);
            TimeUnit.MILLISECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void syncMethodTimeoutAble() {
        try {
            // 如果超过时间，在 lock 方法内抛出异常，也不会获取到锁
            lock.lock(500);
            System.out.println(Thread.currentThread() + " get the lock.");
            int randomInt = ThreadLocalRandom.current().nextInt(10);
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
