package net.gaox.message.event.base;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p> 异步 EventBus 实现类 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 16:52
 */
public class AsyncEventBus extends EventBus {

    /**
     * 默认的异步广播推送的 EventBus
     */
    private static final String BUS_NAME = "default-async";

    public AsyncEventBus(String busName, ThreadPoolExecutor executor) {
        this(busName, null, executor);
    }

    public AsyncEventBus(ThreadPoolExecutor executor) {
        this(BUS_NAME, null, executor);
    }

    public AsyncEventBus(EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        this(BUS_NAME, exceptionHandler, executor);
    }

    AsyncEventBus(String busName, EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        super(busName, exceptionHandler, executor);
    }

}
