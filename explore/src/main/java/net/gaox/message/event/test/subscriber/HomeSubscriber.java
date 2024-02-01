package net.gaox.message.event.test.subscriber;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.event.base.Subscribe;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p> home 订阅 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 13:28
 */
@Slf4j
public class HomeSubscriber {

    /**
     * 回家场景
     *
     * @param message 消息
     */
    @Subscribe(topic = "home")
    public void goHome(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        log.info("HomeSubscriber.goHome message = {}", message);
    }

    /**
     * 在家场景
     *
     * @param message 消息
     */
    @Subscribe(topic = "home")
    public void atHome(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info("HomeSubscriber.atHome message = {}", message);
    }

    /**
     * 离家场景
     *
     * @param message 消息
     */
    @Subscribe(topic = "home")
    public void leaveHome(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        log.info("HomeSubscriber.leaveHome message = {}", message);
    }

}
