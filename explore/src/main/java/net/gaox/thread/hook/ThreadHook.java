package net.gaox.thread.hook;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p> 程序进程退出时，启动 hook 线程 </p>
 * JVM 进程的退出是由于 JVM 进程中没有活跃的非守护线程，或者收到了系统中断信号
 * 向 JVM 程序注入一个 Hook 线程，在 JVM 进程退出的时候，Hook 线程会启动执行
 * 通过 Runtime 可以为 JVM 注入多个 Hook 线程
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:57
 */
@Slf4j
public class ThreadHook {

    public static void main(String[] args) {

        // 程序关闭时，调用 hook 线程
        Thread hook1 = new Thread(() -> {
            try {
                System.out.println("The hook thread 1 is running.");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The program will exit.");
        });
        Runtime.getRuntime().addShutdownHook(hook1);
        log.info("hook 线程[线程组: {}, 线程优先级: {}, 守护线程: {}]",
                hook1.getThreadGroup(), hook1.getPriority(), hook1.isDaemon());

        // 可以注入多个 hook 线程
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("The hook thread 2 is running.");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The hook thread 2 will exit.");
        }));
        System.out.println("The program will is stopping.");
    }

}
