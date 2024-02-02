package net.gaox.message.driven.sync.router;

import net.gaox.message.driven.channel.Channel;
import net.gaox.message.driven.event.Message;
import net.gaox.message.driven.exception.MessageMatcherException;
import net.gaox.message.driven.router.DynamicRouter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 事件调度器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:29
 */
public class EventDispatcher implements DynamicRouter<Message> {

    /**
     * 保存 Channel 和 Message 之间关系的 map
     */
    private final Map<Class<? extends Message>, Channel> routerTable;

    public EventDispatcher() {
        // 初始化 RouterTable
        this.routerTable = new ConcurrentHashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Message> messageType, Channel<? extends Message> channel) {
        this.routerTable.put(messageType, channel);
    }

    @Override
    public void dispatch(Message message) {
        // RouterTable 中存在该类型的消息处理 channel
        if (routerTable.containsKey(message.getType())) {
            // 直接获取对应的 Channel 处理 Message
            Optional<Channel> optionalChannel = Optional.ofNullable(message)
                    .map(Message::getType).map(routerTable::get);
            if (optionalChannel.isPresent()) {
                optionalChannel.get().dispatch(message);
                return;
            }
        }
        throw new MessageMatcherException("Can't match the channel for [" + message.getType() + "] type");
    }

}
