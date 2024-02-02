package net.gaox.message.driven.channel;

import net.gaox.message.driven.event.Message;

/**
 * <p> 事件处理的接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:12
 */
public interface Channel<E extends Message> {

    /**
     * 负责 Message 的调度
     *
     * @param message
     */
    void dispatch(E message);

}

