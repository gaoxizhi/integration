package net.gaox.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.gaox.model.entity.OrderDetail;
import net.gaox.model.mapper.OrderDetailMapper;
import net.gaox.model.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情 服务实现类
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}