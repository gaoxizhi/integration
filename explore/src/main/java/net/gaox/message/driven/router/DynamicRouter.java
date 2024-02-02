package net.gaox.message.driven.router;

import net.gaox.message.driven.channel.Channel;
import net.gaox.message.driven.event.Message;

/**
 * <p> 调度器接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:22
 */
public interface DynamicRouter<E extends Message> {
    /**
     * 针对每一种 Message 类型注册相关的 Channel，只有找到合适的 Channel 该 Message 才会被处理
     */
    void registerChannel(Class<? extends E> messageType, Channel<? extends E> channel);

    /**
     * 为相应的 Channel 分配 Message
     */
    void dispatch(E message);
}