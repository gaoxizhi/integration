package net.gaox.thread.latch.test;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.latch.CountDownLatchWithCallback;
import net.gaox.thread.latch.Latch;
import net.gaox.thread.latch.WaitTimeoutException;

import java.util.concurrent.TimeUnit;

/**
 * <p> 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 14:20
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Latch latch = new CountDownLatchWithCallback(4, () -> log.info("we are arrived."));
        new Traveling(latch, "gaox", "Bus").start();
        new Traveling(latch, "bill", "Walking").start();
        new Traveling(latch, "annie", "Subway").start();
        new Traveling(latch, "kate", "Bicycle").start();
        // 当前线程（main 线程会进入阻塞，直到四个程序员全部都到达目的地）
        // latch.await();

        // 设置超时时间
        try {
            latch.await(TimeUnit.SECONDS, 9);
            log.info("start a new travel.");
        } catch (WaitTimeoutException e) {
            log.error("wait timeout.", e);
        }
    }

}
