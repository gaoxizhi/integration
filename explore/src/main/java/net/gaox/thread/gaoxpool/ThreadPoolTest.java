package net.gaox.thread.gaoxpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p> 线程池测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-23 11:31
 */
@Slf4j
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        final ThreadPool threadPool = new GaoxPool(2, 4, 8, 1000);
        for (int i = 0; i < 20; i++) {
            int index = i + 1;
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    log.info("thread run index = {} is done.", index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // for (; ; ) {
        //     System.out.println("getActiveCount:" + threadPool.getActiveCount());
        //     System.out.println("getQueueSize:" + threadPool.getQueueSize());
        //     System.out.println("getCoreSize:" + threadPool.getCoreSize());
        //     System.out.println("getMaxSize:" + threadPool.getMaxSize());
        //     System.out.println("======================================");
        //     TimeUnit.SECONDS.sleep(5);
        // }

        // TimeUnit.SECONDS.sleep(12);
        // threadPool.shutdown();

        Thread.currentThread().join();
    }

}
