package net.gaox.model.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.model.entity.Orders;
import net.gaox.model.entity.message.BrokerMessageLog;
import net.gaox.model.enums.OrderSendStatus;
import net.gaox.model.mapper.BrokerMessageLogMapper;
import net.gaox.model.mapper.OrdersMapper;
import net.gaox.model.queue.send.OrderSender;
import net.gaox.model.service.OrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    private final BrokerMessageLogMapper brokerMessageLogMapper;

    private final OrderSender orderSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createOrder(Orders order) {

        //插入订单表
        save(order);
        //插入rabbitmq投递信息日志表
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(String.valueOf(order.getNumber()));
        brokerMessageLog.setMessage(JSONObject.toJSONString(order));
        brokerMessageLog.setStatus(OrderSendStatus.ORDER_SENDING.getCode());
        LocalDateTime now = LocalDateTime.now();
        brokerMessageLog.setCreateTime(now);
        //下一次投递时间
        brokerMessageLog.setNextRetry(now.plusMinutes(1));
        brokerMessageLog.setTryCount(0);

        brokerMessageLogMapper.insert(brokerMessageLog);
        try {
            orderSender.send(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
