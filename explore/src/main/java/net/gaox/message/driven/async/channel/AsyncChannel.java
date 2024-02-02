package net.gaox.message.driven.async.channel;

import net.gaox.message.driven.channel.Channel;
import net.gaox.message.driven.event.Event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> 多线程 异步消息处理接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:05
 */
public abstract class AsyncChannel implements Channel<Event> {

    /**
     * 使用 ExecutorService 多线程的方式提交给 Message
     */
    private final ExecutorService executorService;

    public AsyncChannel() {
        // 默认构造函数，提供了 CPU 的核数 × 2 的线程数量
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    }

    /**
     * 支持自定义的 ExecutorService
     *
     * @param executorService ExecutorService
     */
    public AsyncChannel(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * 采用异步的处理方式执行提交的任务
     * 用 final 关键字对其进行修饰，防止子类在继承之后重写 dispatch 方法
     * 具体处理方法使用 handle 抽象方法去做 (Template Method Design Pattern)
     *
     * @param message 消息
     */
    @Override
    public final void dispatch(Event message) {
        executorService.submit(() -> this.handle(message));
    }

    /**
     * 处理 Message 的抽象方法
     *
     * @param message 消息
     */
    protected abstract void handle(Event message);

    /**
     * 提供关闭 ExecutorService 的方法
     */
    public void stop() {
        if (null != executorService && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

}
