package net.gaox.order.task.sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> 同步方法默认实现 </p>
 *
 * @author gaox·Eric
 * @date 2021/2/28 18:33
 */
public class SynchronizedOrder {
    static int num = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(new Random().nextInt(200));
                } catch (InterruptedException e) {
                }
                System.out.printf("[%s]\t订单号：%s\r\n", Thread.currentThread().getName(), getOrderNo());
            });
        }
        latch.countDown();
        executorService.shutdown();
    }

    /**
     * 同步方法
     *
     * @return 唯一id
     */
    synchronized public static String getOrderNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHMMSS");
        return format.format(new Date()) + num++;
    }
}
