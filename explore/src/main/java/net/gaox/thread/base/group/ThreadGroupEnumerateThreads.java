package net.gaox.thread.base.group;

import java.util.concurrent.TimeUnit;

/**
 * <p> 线程组、线程 enumerate </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:20
 */
public class ThreadGroupEnumerateThreads {
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup group = new ThreadGroup("group");
        ThreadGroup group2 = new ThreadGroup(group, "group2");
        Thread thread = new Thread(group, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }, "MyThread");
        // 守护线程在 main 线程结束的时候，结束运行
        thread.setDaemon(true);
        thread.start();

        TimeUnit.MILLISECONDS.sleep(2);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup[] threadGroups = new ThreadGroup[mainGroup.activeGroupCount()];
        int threadGroupsRecurseSize = mainGroup.enumerate(threadGroups);
        System.out.println("threadGroupsRecurseSize = " + threadGroupsRecurseSize);
        threadGroupsRecurseSize = mainGroup.enumerate(threadGroups, false);
        System.out.println("threadGroupsNotRecurseSize = " + threadGroupsRecurseSize);
        System.out.println("--------------------------");

        Thread[] threads = new Thread[mainGroup.activeCount()];
        int recurseSize = mainGroup.enumerate(threads);
        System.out.println("threadRecurseSize = " + recurseSize);
        recurseSize = mainGroup.enumerate(threads, false);
        System.out.println("threadNotRecurseSize = " + recurseSize);
    }

}
