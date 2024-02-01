package net.gaox.message.event.base;

import java.util.concurrent.Executor;

/**
 * <p> Bus 的实现类，同步的广播推送方式 </p>
 * Thread-Per-Message 用异步处理任务的 Executor 替换 EventBus 中的同步 Executor
 *
 * @author gaox·Eric
 * @date 2024-02-01 10:18
 */
public class EventBus implements Bus {

    /**
     * 维护 Subscriber 的注册表
     */
    private final Registry registry = new Registry();

    /**
     * EventBus的名字
     */
    private String busName;

    /**
     * 默认的 EventBus 的名字
     */
    private final static String DEFAULT_BUS_NAME = "default";

    /**
     * 默认的 topic 的名字
     */
    private final static String DEFAULT_TOPIC = "default-topic";

    /**
     * 分发广播消息到各个 Subscriber 的分发器
     */
    private final Dispatcher dispatcher;

    public EventBus() {
        this(DEFAULT_BUS_NAME);
    }

    public EventBus(String busName) {
        this(busName, null, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(EventExceptionHandler exceptionHandler) {
        this(DEFAULT_BUS_NAME, exceptionHandler, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(String busName, EventExceptionHandler exceptionHandler, Executor executor) {
        this.busName = busName;
        this.dispatcher = Dispatcher.newDispatcher(exceptionHandler, executor);
    }

    /**
     * 将注册 Subscriber 的动作直接委托给 Registry
     */
    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    /**
     * 解除注册 同样委托给Registry
     *
     * @param subscriber 订阅者
     */
    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    /**
     * 提交 Event 到默认的 topic
     *
     * @param event 事件
     */
    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC);
    }

    /**
     * 提交 Event 到指定的 topic
     *
     * @param event 事件
     * @param topic 主题
     */
    @Override
    public void post(Object event, String topic) {
        // 由 Dispatcher 来完成的
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    /**
     * 关闭 EventBus
     */
    @Override
    public void close() {
        this.dispatcher.close();
    }

    /**
     * 获取 EventBus 的名字
     *
     * @return EventBus 的名字
     */
    @Override
    public String getBusName() {
        return this.busName;
    }

}