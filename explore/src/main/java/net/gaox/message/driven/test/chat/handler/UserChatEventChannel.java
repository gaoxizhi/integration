package net.gaox.message.driven.test.chat.handler;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.driven.test.chat.event.UserChatEvent;
import net.gaox.message.driven.async.channel.AsyncChannel;
import net.gaox.message.driven.event.Event;

/**
 * <p> 用户聊天发送消息事件处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 18:28
 */
@Slf4j
public class UserChatEventChannel extends AsyncChannel {
    @Override
    protected void handle(Event message) {
        UserChatEvent event = (UserChatEvent) message;
        log.info("The User[{}] say [{}]", event.getUser().getName(), event.getMessage());
    }

}
