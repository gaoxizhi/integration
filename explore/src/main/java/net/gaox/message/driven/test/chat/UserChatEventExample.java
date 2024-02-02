package net.gaox.message.driven.test.chat;

import net.gaox.message.driven.test.chat.entity.User;
import net.gaox.message.driven.test.chat.event.UserChatEvent;
import net.gaox.message.driven.test.chat.event.UserOfflineEvent;
import net.gaox.message.driven.test.chat.event.UserOnlineEvent;
import net.gaox.message.driven.test.chat.handler.UserChatEventChannel;
import net.gaox.message.driven.test.chat.handler.UserOfflineEventChannel;
import net.gaox.message.driven.test.chat.handler.UserOnlineEventChannel;
import net.gaox.message.driven.async.router.AsyncEventDispatcher;

/**
 * <p> 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 18:39
 */
public class UserChatEventExample {
    public static void main(String[] args) {
        // 定义异步的 Router
        final AsyncEventDispatcher dispatcher = new AsyncEventDispatcher();
        // 为Router 注册 Channel 和 Event 之间的关系
        dispatcher.registerChannel(UserOnlineEvent.class, new UserOnlineEventChannel());
        dispatcher.registerChannel(UserOfflineEvent.class, new UserOfflineEventChannel());
        dispatcher.registerChannel(UserChatEvent.class, new UserChatEventChannel());

        // 启动三个登录聊天室的 User
        new UserChatThread(new User("bill"), dispatcher).start();
        new UserChatThread(new User("gaox"), dispatcher).start();
        new UserChatThread(new User("kate"), dispatcher).start();
    }
}