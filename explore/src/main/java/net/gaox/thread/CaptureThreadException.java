package net.gaox.thread;

import java.util.concurrent.TimeUnit;

/**
 * <p> 自定义捕获异常处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 10:50
 */
public class CaptureThreadException {

    public static void main(String[] args) {

        // 自定义捕获异常的 处理器
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName() + " occur exception");
            e.printStackTrace();
        });

        // get current thread's thread group
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println(mainGroup.getName());
        System.out.println(mainGroup.getParent());
        System.out.println(mainGroup.getParent().getParent());

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }

            //here will throw unchecked exception.
            System.out.println(1 / 0);
        }, "Test-Thread").start();
    }

}
