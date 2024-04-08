package net.gaox.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.gaox.model.entity.Order;

/**
 * <p> 订单表 服务类 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
public interface OrderService extends IService<Order> {
    Boolean createOrder(Order order);

}