package net.gaox.work.thread.v1.message;

import net.gaox.work.thread.service.OrderService;

import java.util.Map;

/**
 * <p> 异步执行下单详情 消息处理类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:30
 */
public class OrderMessage extends MethodMessage {
    public OrderMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        // 方法参数
        String account = (String) params.get("account");
        long orderId = (long) params.get("orderId");
        // 调用执行
        orderService.order(account, orderId);
    }

}
