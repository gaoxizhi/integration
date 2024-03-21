package net.gaox.thread.hook;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * <p> 设置子线程未捕获的异常处理 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-05 15:01
 */
@Slf4j
public class UncaughtExceptionHandler {
    private final static int A = 10;
    private final static int B = 0;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5_000L);
                int result = A / B;
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(s -> !s.isNativeMethod())
                .map(s -> s.getClassName() + " : " + s.getMethodName() + " : " + s.getLineNumber())
                .forEach(log::info);

        // 未捕获的异常处理
        t.setUncaughtExceptionHandler((thread, e) -> {
            log.info("thread: {}", thread);
            Arrays.stream(Thread.currentThread().getStackTrace())
                    .filter(s -> !s.isNativeMethod())
                    .map(s -> s.getClassName() + " : " + s.getMethodName() + " : " + s.getLineNumber())
                    .forEach(log::info);

            log.error("uncaught exception: ", e);
        });
        t.start();
    }

}
