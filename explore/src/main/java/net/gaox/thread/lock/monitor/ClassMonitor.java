package net.gaox.thread.lock.monitor;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

/**
 * <p> 同步方法和 class 的区别 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:35
 */
public class ClassMonitor {

    public static void main(String[] args) {
        new Thread(ClassMonitor::synchronizedMethod, "T1").start();
        new Thread(ClassMonitor::synchronizedClassCodeBlock, "T2").start();
    }

    public static synchronized void synchronizedMethod() {
        System.out.println(currentThread().getName() + " enter to method1");
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void synchronizedClassCodeBlock() {
        // 与同步方法监视相同的对象
        synchronized (ClassMonitor.class) {
            System.out.println(currentThread().getName() + " enter to method2");
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
