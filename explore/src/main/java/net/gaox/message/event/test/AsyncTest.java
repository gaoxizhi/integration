package net.gaox.message.event.test;

import net.gaox.message.event.base.AsyncEventBus;
import net.gaox.message.event.base.EventBus;
import net.gaox.message.event.test.subscriber.DefaultSubscriber;
import net.gaox.message.event.test.subscriber.HomeSubscriber;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p> 异步测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 13:21
 */
public class AsyncTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        EventBus bus = new AsyncEventBus("testBus", executor);
        bus.register(new DefaultSubscriber());
        bus.register(new HomeSubscriber());

        bus.post("gaoxizhi", "home");
        bus.post("Hello, World!");
    }

}
