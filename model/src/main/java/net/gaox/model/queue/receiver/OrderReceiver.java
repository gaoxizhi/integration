package net.gaox.model.queue.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import net.gaox.model.entity.Orders;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p> 订单处理 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 12:20
 */
@Slf4j
@Component
public class OrderReceiver {

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
        log.info("----收到消息，开始处理 ===> 订单号：" + order.getNumber());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        // 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
        // 如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
        channel.basicAck(deliveryTag, false);
        log.info("--------处理完成--------");
    }

}
