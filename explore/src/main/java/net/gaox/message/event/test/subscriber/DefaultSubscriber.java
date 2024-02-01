package net.gaox.message.event.test.subscriber;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.event.base.Subscribe;

/**
 * <p> 订阅者类 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 13:22
 */
@Slf4j
public class DefaultSubscriber {

    /**
     * 订阅默认主题
     *
     * @param message 消息
     */
    @Subscribe
    public void defaultMethod(String message) {
        log.info("DefaultSubscriber.defaultMethod message = {}", message);
    }

}
