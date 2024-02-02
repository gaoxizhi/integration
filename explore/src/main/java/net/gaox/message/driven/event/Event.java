package net.gaox.message.driven.event;

/**
 * <p> 其他 事件 Message 的基类（类似于适配器模式） </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:26
 */
public class Event implements Message {
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

}
