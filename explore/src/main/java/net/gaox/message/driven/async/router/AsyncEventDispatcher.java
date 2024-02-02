package net.gaox.message.driven.async.router;

import net.gaox.message.driven.async.channel.AsyncChannel;
import net.gaox.message.driven.event.Event;
import net.gaox.message.driven.exception.MessageMatcherException;
import net.gaox.message.driven.channel.Channel;
import net.gaox.message.driven.router.DynamicRouter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 异步事件调度器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:29
 */
public class AsyncEventDispatcher implements DynamicRouter<Event> {

    /**
     * 保存 Channel 和 Message 之间关系的 map
     */
    private final Map<Class<? extends Event>, AsyncChannel> routerTable;

    public AsyncEventDispatcher() {
        this.routerTable = new ConcurrentHashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Event> messageType, Channel<? extends Event> channel) {
        // 在 AsyncEventDispatcher 中，Channel 必须是 AsyncChannel 类型
        if (!(channel instanceof AsyncChannel)) {
            throw new IllegalArgumentException("The channel must be AsyncChannel Type.");
        }
        this.routerTable.put(messageType, (AsyncChannel) channel);
    }

    @Override
    public void dispatch(Event message) {
        if (!routerTable.containsKey(message.getType())) {
            throw new MessageMatcherException("Can't match the channel for [" + message.getType() + "] type");
        }
        routerTable.get(message.getType()).dispatch(message);
    }

    public void shutdown() {
        // 关闭所有的 AsyncChannel 以释放资源
        routerTable.values().forEach(AsyncChannel::stop);
    }

}
