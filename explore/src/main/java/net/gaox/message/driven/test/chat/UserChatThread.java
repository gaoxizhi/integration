package net.gaox.message.driven.test.chat;

import net.gaox.message.driven.test.chat.entity.User;
import net.gaox.message.driven.test.chat.event.UserChatEvent;
import net.gaox.message.driven.test.chat.event.UserOfflineEvent;
import net.gaox.message.driven.test.chat.event.UserOnlineEvent;
import net.gaox.message.driven.async.router.AsyncEventDispatcher;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p> 用户聊天线程 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 18:33
 */
public class UserChatThread extends Thread {
    private final User user;
    private final AsyncEventDispatcher dispatcher;

    public UserChatThread(User user, AsyncEventDispatcher dispatcher) {
        super("user-" + user.getName());
        this.user = user;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try {
            // 用户上线事件
            dispatcher.dispatch(new UserOnlineEvent(user));
            for (int i = 0; i < 5; i++) {
                // 用户发送消息事件
                UserChatEvent userChatEvent = new UserChatEvent(user, getName() + "-Hello-" + i);
                dispatcher.dispatch(userChatEvent);
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 用户下线事件
            dispatcher.dispatch(new UserOfflineEvent(user));
        }
    }

}
