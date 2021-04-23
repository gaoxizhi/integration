package net.gaox.javaSE2018.thread.concurrency.chapter7;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class SynchronizedTest {

    private final static Object LOCK = new Object();

    public static void main(String[] args) {

        Runnable runnable = () -> {
            synchronized (LOCK) {
                try {
                    Thread.sleep(200_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();

    }
}
