package net.gaox.message.event.base;

/**
 * <p> 消息中间件 Bus 接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 10:02
 */
public interface Bus {

    /**
     * 将某个对象实例注册给 Event Bus，从此之后该类就成为 Subscriber 了
     *
     * @param subscriber 订阅者
     */
    void register(Object subscriber);

    /**
     * 将某个对象从Bus上取消注册，会在Event Bus的注册表（Registry）中将其移除
     * 取消注册之后就不会再接收到来自 Bus 的任何消息
     *
     * @param subscriber 订阅者
     */
    void unregister(Object subscriber);

    /**
     * 提交 Event 到默认的 topic 提交 Event 到 Event Bus 中
     *
     * @param event 消息
     */
    void post(Object event);

    /**
     * 提交Event到指定的topic 提交Event的同时指定了topic
     *
     * @param Event 消息
     * @param topic 主题
     */
    void post(Object Event, String topic);

    /**
     * 关闭该bus 销毁该Event Bus
     */
    void close();

    /**
     * 获取Event Bus的名称
     *
     * @return busName
     */
    String getBusName();

}
