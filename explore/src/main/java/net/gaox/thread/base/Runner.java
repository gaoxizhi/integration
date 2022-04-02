package net.gaox.thread.base;

import java.util.concurrent.*;

/**
 * <p> Runnable </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:37
 */
public class Runner implements Runnable {
    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(new Caller());
        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("timeout");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 强制终止任务
            executor.shutdownNow();
        }
    }
}