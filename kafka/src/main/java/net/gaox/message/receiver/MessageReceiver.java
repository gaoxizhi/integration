package net.gaox.message.receiver;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.conf.KafkaConfig;
import net.gaox.domain.model.entity.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p> Kafka 消费者 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-09 21:33
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReceiver {

    private final KafkaConfig kafkaConfig;

    /**
     * 监听topic主题，消费消息
     * topics 配置主题，2.3以上版本可使用通配符
     * groupId 配置消费组
     * SendTo 配置发送到哪个主题
     *
     * @param record 消息
     */
    @KafkaListener(topics = "#{kafkaConfig.topic}", groupId = "gaox")
    @SendTo("check_topic")
    public String process(ConsumerRecord<?, ?> record) {
        Order order = null;
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            order = JSON.parseObject(message.toString(), Order.class);
            order.setNote("at process");
            log.info("kafka log processOrder: {}", JSON.toJSONString(order));
            order.setUpdateTime(LocalDateTime.now());
        }
        return JSON.toJSONString(order);
    }

    /**
     * 监听check_topic主题，消费消息
     * errorHandler 配置异常处理
     *
     * @param record 消息
     */
    @KafkaListener(topics = "check_topic", groupId = "gaox_check", errorHandler = "listenerErrorHandler")
    public void check(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            Order order = JSON.parseObject(message.toString(), Order.class);
            order.setNote("at check");
            log.info("kafka log check : {}", JSON.toJSONString(order));
            throw new RuntimeException("我就是玩儿～");
        }
    }

}
