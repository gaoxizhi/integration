package net.gaox.thread.multi;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2021/4/23 23:14
 */
public class MultiThreadingExecuteTest {
    static int intNum = 0;
    static volatile int intVolatileNum = 0;
    static AtomicInteger atomicNum = new AtomicInteger(0);
    static volatile AtomicInteger atomicVolatileNum = new AtomicInteger(0);

    @Test
    public void staticAddTest() {
        staticAdd();
        System.out.println();
        System.out.println(intNum);
    }

    public void staticAdd() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(0);
                    Thread.sleep(new Random().nextInt(20));
                    intNum++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    public void staticAddTest2() throws InterruptedException {
        staticAdd2();
        System.out.println();
        System.out.println(intNum);
    }

    public void staticAdd2() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(3));
                    intNum++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            // 加入主线程
            thread.join();
        }
    }

    @Test
    public void increaseIntNum() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(0);
                    Thread.sleep(1);
//                    Thread.sleep(new Random().nextInt(20));
                    Num.increaseIntNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println(Num.getIntNum());
    }

    @Test
    public void increaseIntVolatileNum() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        AtomicInteger k = new AtomicInteger();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.print("#" + Thread.currentThread().getName().split("-")[1]);
                    Thread.sleep(50);
                    Num.increaseIntVolatileNum();
                    k.getAndIncrement();
                    System.out.print("*");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        System.out.println(k);
        System.out.println("num is: " + Num.getIntVolatileNum());
        System.out.println(k);
        Thread.sleep(2);
        System.out.println(k);
        System.out.println("num2 is: " + Num.getIntVolatileNum());
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    public void increaseAtomicNum() {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                try {
//                    Thread.sleep(0);
                    Thread.sleep(1);
                    Num.increaseAtomicNum();
//                    Thread.sleep(new Random().nextInt(20));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println(Num.getAtomicNum());
    }

    @Test
    public void increaseAtomicVolatileNum() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(20));
                    Num.increaseAtomicVolatileNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println(Num.getAtomicVolatileNum());
    }
}
