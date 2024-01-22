package net.gaox.thread.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 能够创建的最大线程数 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-19 11:09
 */
@Slf4j
public class ThreadCounter {

    final static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        createThread();
        Thread alwaysSleep = alwaysSleep();
        alwaysSleep.start();

        System.out.println("test");
        ThreadGroup group = new ThreadGroup("TestGroup");
        Runnable runnable = getRecursionRunnable();

        // 一般情况下，创建线程的时候不会手动指定栈内存的地址空间字节数组，统一通过xss参数进行设置即可
        // stackSize 越大则代表着正在线程内方法调用递归的深度就越深，stackSize 越小则代表着创建的线程数量越多
        // 当然了这个参数对平台的依赖性比较高，比如不同的操作系统、不同的硬件。
        // 在有些平台下，越高的stack设定，可以允许的递归深度越多；反之，越少的stack设定，则递归深度越浅
        int stackSize = 409600;
        Thread thread = new Thread(group, runnable, "TestThread", stackSize);
        thread.start();

        Thread innerThreadSleep = innerThreadSleep();
        innerThreadSleep.start();
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println("thread state = " + innerThreadSleep.getState());

    }

    private static Runnable getRecursionRunnable() {
        Runnable runnable = new Runnable() {
            final int MAX = 3 * 512;

            @Override
            public void run() {

                int i = 0;
                recurse(i);
            }

            private void recurse(int i) {
                System.out.printf("current recurse = %4d\n", i);
                if (i < MAX) {
                    recurse(i + 1);
                }
            }
        };
        return runnable;
    }

    private static void createThread() {
        try {
            while (true) {

                new Thread(() -> {
                    try {
                        log.info("The " + counter.getAndIncrement() + " thread be created.");
                        TimeUnit.MINUTES.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                TimeUnit.MILLISECONDS.sleep(2);
            }
        } catch (Throwable e) {
            System.out.println("failed At=>" + counter.get());
            System.exit(1);
        }
    }

    private static Thread innerThreadSleep() {
        Thread t = new Thread(() ->
        {
            Thread innerThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            innerThread.start();
        });
        return t;
    }

    private static Thread alwaysSleep() {
        Thread thread = new Thread(() ->
        {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return thread;
    }
}
