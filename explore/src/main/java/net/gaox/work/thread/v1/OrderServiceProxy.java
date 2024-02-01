package net.gaox.work.thread.v1;

import net.gaox.thread.future.Future;
import net.gaox.work.thread.base.ActiveFuture;
import net.gaox.work.thread.service.OrderService;
import net.gaox.work.thread.v1.message.FindOrderDetailsMessage;
import net.gaox.work.thread.v1.message.MethodMessage;
import net.gaox.work.thread.v1.message.OrderMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> OrderService实现的异步处理代理类 </p>
 * 将 OrderService 的每一个方法都封装成 MethodMessage，然后提交给 ActiveMessage 队列
 * 在使用 OrderService 接口方法的时候，实际上是在调用 OrderServiceProxy 中的方法
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:27
 */
public class OrderServiceProxy implements OrderService {
    private final OrderService orderService;
    private final MethodMessageQueue methodMessageQueue;

    public OrderServiceProxy(OrderService orderService, MethodMessageQueue methodMessageQueue) {
        this.orderService = orderService;
        this.methodMessageQueue = methodMessageQueue;
    }

    @Override
    public Future<String> findOrderDetails(long orderId) {
        // 定义一个 ActiveFuture，并且可支持立即返回
        final ActiveFuture<String> activeFuture = new ActiveFuture<>();
        // 收集方法入参以及返回的 ActiveFuture 封装成 MethodMessage
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("activeFuture", activeFuture);
        MethodMessage message = new FindOrderDetailsMessage(params, orderService);
        // 将 MethodMessage 保存至 activeMessageQueue 中
        methodMessageQueue.offer(message);
        return activeFuture;
    }

    @Override
    public void order(String account, long orderId) {
        // 收集方法参数，并且封装成 MethodMessage，然后 offer 至队列中
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        params.put("orderId", orderId);
        MethodMessage message = new OrderMessage(params, orderService);
        methodMessageQueue.offer(message);
    }

}
