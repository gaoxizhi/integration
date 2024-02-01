package net.gaox.work.thread.v1;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.future.Future;
import net.gaox.thread.future.FutureService;
import net.gaox.work.thread.service.OrderService;

import java.util.concurrent.TimeUnit;

/**
 * <p> 订单接口实现异步处理 v1 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 11:59
 */
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Override
    public Future<String> findOrderDetails(long orderId) {
        // Future 返回结果
        return FutureService.<Long, String>newService().submit(input -> {
            try {
                //通过休眠来模拟该方法的执行比较耗时
                TimeUnit.SECONDS.sleep(10);
                log.info("process the orderID -> {}", orderId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return String.format("The order [%08d] Details Information.", orderId);
        }, orderId, null);
    }

    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(10);
            log.info("process the order for account = {}, orderId  = {}.", account, orderId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
