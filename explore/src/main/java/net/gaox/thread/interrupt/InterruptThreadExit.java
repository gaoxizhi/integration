package net.gaox.thread.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * <p> 中断退出当前任务 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-19 19:32
 */
public class InterruptThreadExit {
    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            System.out.println("I will start work");

            for (; ; ) {
                //working
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }

            // 等同上面的 catch 中断
            /// while (!Thread.currentThread().isInterrupted()) {
            //     // working
            // }

            System.out.println("I will be exiting.");
        });

        t.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("System will be shutdown.");
        t.interrupt();
    }

}
