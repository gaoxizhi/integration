package net.gaox.message.event.base;

import java.lang.reflect.Method;

/**
 * <p> 事件上下文 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 12:08
 */
public interface EventContext {

    /**
     * 获取消息源
     *
     * @return 消息源
     */
    String getSource();

    /**
     * 获取消息体
     *
     * @return 消息体
     */
    Object getSubscriber();

    /**
     * 获取订阅方法
     *
     * @return 订阅方法
     */
    Method getSubscribe();

    /**
     * 获取事件
     *
     * @return 事件
     */
    Object getEvent();

}
