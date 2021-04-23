package net.gaox.order.task.atomic;

import net.gaox.order.OrderAtomicServer;
import net.gaox.order.OrderServer;
import net.gaox.order.task.OrderTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> 类锁、对象锁 </p>
 * 类锁只有一个：OrderLockServer.class
 * 对象锁每创建一个都是一个新的锁
 *
 * @author gaox·Eric
 * @date 2021/3/1 21:16w
 */
public class JvmAtomicOrder {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch latch = new CountDownLatch(1);
        OrderServer orderServer = new OrderAtomicServer();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new OrderTask(latch, orderServer));
        }
        latch.countDown();
        executorService.shutdown();
    }
}
