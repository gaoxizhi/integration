package net.gaox.work.thread.v1;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.future.Future;
import net.gaox.work.thread.service.OrderService;

/**
 * <p> 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 13:20
 */
@Slf4j
public class OrderTest {

    public static void main(String[] args) throws InterruptedException {
        // 在创建 OrderService 时需要传递 OrderService 接口的具体实现
        OrderService orderService = OrderServiceFactory.toActiveObject(new OrderServiceImpl());
        orderService.order("hello", 18567);
        // 立即返回
        log.info("order Return immediately");

        Future<String> orderDetails = orderService.findOrderDetails(18569);
        log.info("orderDetails Return immediately");
        // 这里会阻塞，直到 get 到结果
        String details = orderDetails.get();
        log.info("details = {}", details);
    }

}
