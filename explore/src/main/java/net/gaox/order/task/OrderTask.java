package net.gaox.order.task;

import net.gaox.order.OrderServer;

import java.util.concurrent.CountDownLatch;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2021/2/28 18:33
 */
public class OrderTask implements Runnable {
    private CountDownLatch latch;
    private OrderServer orderServer;

    public OrderTask(CountDownLatch latch, OrderServer orderServer) {
        this.latch = latch;
        this.orderServer = orderServer;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("[%s]\t订单号：%s\r\n", Thread.currentThread().getName(), orderServer.getOrderNo());
    }
}
