package net.gaox.message.driven.test.chat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.gaox.message.driven.test.chat.entity.User;
import net.gaox.message.driven.event.Event;

/**
 * <p> 用户上线事件 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 18:22
 */
@Getter
@AllArgsConstructor
public class UserOnlineEvent extends Event {

    /**
     * 用户
     */
    private final User user;

}
