package net.gaox.model.queue.send;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.gaox.model.entity.Order;
import net.gaox.model.entity.message.BrokerMessageLog;
import net.gaox.model.enums.OrderSendStatusEnum;
import net.gaox.model.events.OrderSendEvent;
import net.gaox.model.mapper.BrokerMessageLogMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <p> 订单消息发布 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 13:16
 */
@Slf4j
@Component
public class OrderSender {

    public static String exchange = "order-exchange";
    public static String orderRoutingKey = "order.abcd";
    private final RabbitTemplate rabbitTemplate;

    private final BrokerMessageLogMapper brokerMessageLogMapper;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        /**
         *
         * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
         * @param ack  是否投递成功
         * @param cause 失败原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();
            LambdaQueryWrapper<BrokerMessageLog> queryWrapper = new LambdaQueryWrapper<BrokerMessageLog>()
                    .eq(BrokerMessageLog::getMessageId, messageId);

            BrokerMessageLog brokerMessageLog = null;
            try {
                brokerMessageLog = brokerMessageLogMapper.selectList(queryWrapper).get(0);
            } catch (IndexOutOfBoundsException e) {
                log.error("不存在messageId:{}的日志记录", messageId);
            }

            //返回成功，表示消息被正常投递
            if (ack) {
                brokerMessageLog.setStatus(OrderSendStatusEnum.ORDER_SEND_SUCCESS.getCode());
                brokerMessageLog.setUpdateTime(LocalDateTime.now());
                brokerMessageLogMapper.updateById(brokerMessageLog);
                log.info("信息投递成功，messageId:{}", messageId);
            } else {
                log.error("消费信息失败，messageId:{} 原因:{}", messageId, cause);
            }
        }
    };

    public OrderSender(RabbitTemplate rabbitTemplate, BrokerMessageLogMapper brokerMessageLogMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.brokerMessageLogMapper = brokerMessageLogMapper;
    }

    /**
     * 信息投递的方法
     *
     * @param order 订单
     */
    public void send(Order order) {
        //设置投递回调
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(order.getNumber()));
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, order, correlationData);
    }

    /**
     * 信息投递的事件监听
     *
     * @param event 订单事件消息
     */
    @Async
    @org.springframework.core.annotation.Order(0)
    @EventListener
    public void eventListener(OrderSendEvent event) {
        if (null == event || null == event.getEventType() || null == event.getOrder()) {
            log.warn("事件参数异常.");
            return;
        }
        if (!OrderSendStatusEnum.ORDER_SENDING.equals(event.getEventType())) {
            log.warn("不执行当前订单[{}]事件.", event.getEventType().getDesc());
            return;
        }
        log.info("订单[{}]开始投递.", event.getOrder().getNumber());
        send(event.getOrder());
    }

}
