package net.gaox.consumer.queue.receiver;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.model.entity.Orders;
import net.gaox.model.service.OrdersService;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * <p> 订单处理 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 12:20
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderReceiver {

    private final OrdersService ordersService;

    /**
     * 监听订单消息队列
     *
     * @param order   订单内容
     * @param headers mq 消息头
     * @param channel 通知通道
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(key = "order.*",
            value = @Queue(value = "order-queue", durable = "true"),
            exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic")
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload Orders order, @Headers Map<String, Object> headers,
                               Channel channel) throws Exception {
        String orderNumber = Optional.ofNullable(order).map(Orders::getNumber).orElse("未知");
        log.info("----收到消息，开始处理 ===> 订单号：[{}], 内容: {}", orderNumber, order);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        Orders localOrder = ordersService.getById(order.getId());
        // 订单不存在，丢弃消息
        if (null == localOrder) {
            log.error("订单不存在，丢弃消息，订单号：{}", orderNumber);
            channel.basicAck(deliveryTag, false);
            return;
        }
        // 订单备注不为空，说明已经处理过，直接确认消息
        if (StringUtils.isNotBlank(localOrder.getNote())) {
            log.info("订单[{}]已完成处理.", orderNumber);
            channel.basicAck(deliveryTag, false);
            return;
        }

        localOrder.setNote("完成");
        ordersService.updateById(localOrder);

        // 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
        // 如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
        channel.basicAck(deliveryTag, false);
        log.info("--------处理完成--------");
    }

}
