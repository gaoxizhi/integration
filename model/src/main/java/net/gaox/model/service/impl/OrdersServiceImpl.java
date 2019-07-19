package net.gaox.model.service.impl;

import net.gaox.model.entity.Orders;
import net.gaox.model.mapper.OrdersMapper;
import net.gaox.model.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author gaoxÂ·Eric
 * @since 2019-07-13
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
