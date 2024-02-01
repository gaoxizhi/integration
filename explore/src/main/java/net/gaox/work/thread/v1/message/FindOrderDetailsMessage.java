package net.gaox.work.thread.v1.message;

import net.gaox.thread.future.Future;
import net.gaox.work.thread.service.OrderService;
import net.gaox.work.thread.base.ActiveFuture;

import java.util.Map;

/**
 * <p> 异步执行订单详情 消息处理类 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:26
 */
public class FindOrderDetailsMessage extends MethodMessage {

    public FindOrderDetailsMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        // 执行 orderService 的 findOrderDetails 方法
        Future<String> realFuture = orderService.findOrderDetails((Long) params.get("orderId"));
        ActiveFuture<String> activeFuture = (ActiveFuture<String>) params.get("activeFuture");
        try {
            // 调用 orderServiceImpl 返回的 Future.get（）
            // 此方法会导致阻塞直到 findOrderDetails 方法完全执行结束
            String result = realFuture.get();
            // 当 findOrderDetails 执行结束时，将结果通过 finish 的方法传递给 activeFuture
            activeFuture.finish(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
            activeFuture.finish(null);
        }
    }

}
