package net.gaox.thread.base.group;

import java.util.concurrent.TimeUnit;

/**
 * <p> 线程组和线程优先级 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:19
 */
public class ThreadGroupPriority {
    public static void main(String[] args) {

        ThreadGroup group = new ThreadGroup("group1");
        Thread thread = new Thread(group, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread");
        thread.setDaemon(true);
        thread.start();
        System.out.println("group.getMaxPriority()=" + group.getMaxPriority());
        System.out.println("thread.getPriority()=" + thread.getPriority());
        // 设置线程组的最大优先级，此后线程组下的线程优先级最大为此值(不影响早期创建的线程优先级)
        group.setMaxPriority(3);
        Thread thread2 = new Thread(group, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2");
        thread2.setDaemon(true);
        thread2.start();
        System.out.println("group.getMaxPriority()=" + group.getMaxPriority());
        System.out.println("thread.getPriority()=" + thread.getPriority());
        System.out.println("thread2.getPriority()=" + thread2.getPriority());
    }

}
