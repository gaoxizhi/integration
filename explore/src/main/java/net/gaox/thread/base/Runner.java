package net.gaox.thread.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * <p> Runnable </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:37
 */
@Slf4j
public class Runner {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(new Caller());
        log.info("executor submit wait future");
        try {
            // 判断任务完成
            // Boolean futureDone = future.isDone();
            // get 方法会阻塞
            // Boolean get = future.get();

            // 或设置超时时间，超时可能会报错，任务完成会立即返回
            Boolean get = future.get(20, TimeUnit.SECONDS);
            log.info("get status = {}", get);
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