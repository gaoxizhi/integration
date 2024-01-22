package net.gaox.thread.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p> ThreadGroup </p>
 *
 * @author gaox·Eric
 * @date 2024-01-19 10:17
 */
@Slf4j
public class ThreadGroupTest {
    public static void main(String[] args) {

        // main线程所在的ThreadGroup称为main
        Thread mainThread = Thread.currentThread();
        ThreadGroup mainThreadGroup = mainThread.getThreadGroup();
        log.info("main thread belong group: {}", mainThreadGroup.getName());

        Thread t1 = new Thread("t1");
        ThreadGroup t1ThreadGroup = t1.getThreadGroup();
        // 构造一个线程的时候如果没有显式地指定ThreadGroup，那么它将会和父线程同属于一个ThreadGroup
        log.info("t1 and main belong the same group: {}", mainThreadGroup == t1ThreadGroup);
        // 它还会和父线程拥有同样的优先级，同样的daemon
        log.info("main maxPriority: {}, isDaemon: {}", mainThread.getPriority(), mainThread.isDaemon());
        log.info("t1 maxPriority: {}, isDaemon: {}", t1.getPriority(), t1.isDaemon());

        ThreadGroup group = new ThreadGroup("TestGroup");
        Thread t2 = new Thread(group, () -> System.out.println("hou hou ~ "), "t2");
        // 通过 setDaemon 方法将 t2 设置为了守护线程
        // 那么 main 进程结束生命周期后，JVM 也会随之退出运行，当然 t2 线程也会结束
        t2.setDaemon(true);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8,
                60, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
        threadPoolExecutor.execute(t2);
        log.info("t2 thread group not belong main group: {}", mainThreadGroup == t2.getThreadGroup());
        log.info("t2 thread group belong main TestGroup: {}", group == t2.getThreadGroup());
    }
}
