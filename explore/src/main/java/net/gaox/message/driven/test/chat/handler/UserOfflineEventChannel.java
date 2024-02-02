package net.gaox.message.driven.test.chat.handler;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.driven.test.chat.event.UserOfflineEvent;
import net.gaox.message.driven.async.channel.AsyncChannel;
import net.gaox.message.driven.event.Event;

/**
 * <p> 用户下线事件处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 18:28
 */
@Slf4j
public class UserOfflineEventChannel extends AsyncChannel {
    @Override
    protected void handle(Event message) {
        UserOfflineEvent event = (UserOfflineEvent) message;
        log.info("The User[{}] is offline.", event.getUser().getName());
    }

}
