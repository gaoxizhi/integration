package net.gaox.javaSE2018.thread.concurrency.chapter2;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 */
public class TicketWindowRunnable implements Runnable {

    private int index = 1;

    private final static int MAX = 50;

    @Override
    public void run() {

        while (index <= MAX) {
            System.out.println(Thread.currentThread() + " 的号码是:" + (index++));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
