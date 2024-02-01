package net.gaox.work.thread.v2;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.future.Future;
import net.gaox.work.thread.service.OrderService;

/**
 * <p> 测试异步处理 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 14:35
 */
@Slf4j
public class OrderTest {
    public static void main(String[] args) throws InterruptedException {
        // 在创建 OrderService 时需要传递 OrderService 接口的具体实现
        OrderService orderService = ActiveServiceFactory.active(new OrderServiceImpl());
        log.info("orderService = {}", orderService);
        log.info("orderService class = {}", orderService.getClass());
        log.info("orderService class name = {}", orderService.getClass().getName());

        Future<String> future = orderService.findOrderDetails(18567);
        log.info("i will be returned immediately");
        log.info("result = {}", future.get());
    }

}
