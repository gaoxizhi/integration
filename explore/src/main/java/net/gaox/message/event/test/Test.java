package net.gaox.message.event.test;

import net.gaox.message.event.base.EventBus;
import net.gaox.message.event.test.subscriber.DefaultSubscriber;
import net.gaox.message.event.test.subscriber.HomeSubscriber;

/**
 * <p> 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 13:21
 */
public class Test {
    public static void main(String[] args) {
        EventBus bus = new EventBus("testBus");
        bus.register(new DefaultSubscriber());
        bus.register(new HomeSubscriber());

        bus.post("gaoxizhi", "home");
        bus.post("Hello, World!");
    }

}
