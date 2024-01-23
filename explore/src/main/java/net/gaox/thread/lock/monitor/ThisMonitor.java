package net.gaox.thread.lock.monitor;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

/**
 * <p> 同步方法和 this 的区别 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:30
 */
public class ThisMonitor {

    public static void main(String[] args) {
        ThisMonitor thisMonitor = new ThisMonitor();
        new Thread(thisMonitor::synchronizedMethod, "T1").start();
        new Thread(thisMonitor::synchronizedThisCodeBlock, "T2").start();
    }

    public synchronized void synchronizedMethod() {
        System.out.println(currentThread().getName() + " enter to method1");
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void synchronizedThisCodeBlock() {
        synchronized (this) {
            System.out.println(currentThread().getName() + " enter to method2");
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
