package net.gaox.thread.base.group;

import java.util.concurrent.TimeUnit;

/**
 * <p> 守护线程组织 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:05
 */
public class ThreadGroupDaemon {
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup group1 = new ThreadGroup("Group1");
        new Thread(group1, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "group1-thread1").start();

        ThreadGroup group2 = new ThreadGroup("Group2");
        new Thread(group2, () -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    //received interrupt SIGNAL and clear quickly
                    break;
                }
            }
            System.out.println("group2-thread1 will exit.");
        }, "group2-thread1").start();

        //设置daemon为true
        group2.setDaemon(true);

        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println("mainGroup.activeCount=" + mainGroup.activeCount());
        System.out.println("mainGroup.activeGroupCount=" + mainGroup.activeGroupCount());
        System.out.println("mainGroup.getMaxPriority=" + mainGroup.getMaxPriority());
        System.out.println("mainGroup.getName=" + mainGroup.getName());
        System.out.println("mainGroup.getParent=" + mainGroup.getParent());
        System.out.println("--------------------------");
        mainGroup.list();
        System.out.println("--------------------------");
        System.out.println("mainGroup.parentOf(group1) = " + mainGroup.parentOf(group1));
        System.out.println("group1.parentOf(mainGroup) = " + group1.parentOf(mainGroup));
        System.out.println("group1.parentOf(group1) = " + group1.parentOf(group1));

        System.out.println("--------------------------");
        TimeUnit.SECONDS.sleep(3);
        System.out.println("group1.isDestroyed = " + group1.isDestroyed());
        System.out.println("group2.isDestroyed = " + group2.isDestroyed());
        // 线程组 group1 被销毁
        group1.destroy();
        System.out.println("after destroy, group1.isDestroyed = " + group1.isDestroyed());
        // group2 中断，导致 group2-thread1 捕获中断异常
        group2.interrupt();

    }

}
