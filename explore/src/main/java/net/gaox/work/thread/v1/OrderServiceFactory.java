package net.gaox.work.thread.v1;

import net.gaox.work.thread.service.OrderService;

/**
 * <p> 异步消息工厂方法 </p>
 * 初始化消息队列，代理构建处理方法
 *
 * @author gaox·Eric
 * @date 2024-01-31 13:22
 */
public class OrderServiceFactory {

    /**
     * 将 MethodMessageQueue 定义成 static 保持其在整个 JVM 进程中是唯一的
     * ActiveDaemonThread 会在此刻启动
     */
    private final static MethodMessageQueue METHOD_MESSAGE_QUEUE = new MethodMessageQueue();

    /**
     * 不允许外部通过 new 的方式构建
     */
    private OrderServiceFactory() {
    }


    /**
     * 返回 OrderServiceProxy
     *
     * @param orderService service
     * @return OrderService实现
     */
    public static OrderService toActiveObject(OrderService orderService) {
        return new OrderServiceProxy(orderService, METHOD_MESSAGE_QUEUE);
    }

}
