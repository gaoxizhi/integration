package net.gaox.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.model.entity.Order;
import net.gaox.model.entity.message.BrokerMessageLog;
import net.gaox.model.enums.OrderSendStatusEnum;
import net.gaox.model.events.OrderSendEvent;
import net.gaox.model.mapper.BrokerMessageLogMapper;
import net.gaox.model.mapper.OrderMapper;
import net.gaox.model.service.OrderService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * <p> 订单表 服务实现类 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final BrokerMessageLogMapper brokerMessageLogMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createOrder(Order order) {
        //插入订单表
        baseMapper.insert(order);
        //插入rabbitmq投递信息日志表
        BrokerMessageLog log = BrokerMessageLog.fromOrder(order);
        brokerMessageLogMapper.insert(log);
        publisherOrderSendEvent(order);
        return true;
    }

    private void publisherOrderSendEvent(Orders order) {
        try {
            OrderSendEvent even = new OrderSendEvent(OrderSendStatusEnum.ORDER_SENDING, order);
            applicationEventPublisher.publishEvent(even);
        } catch (Exception e) {
            log.error("订单发送事件发布失败，订单号：{}", order.getNumber(), e);
        }
    }

}
