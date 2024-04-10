package net.gaox.message.receiver;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.model.entity.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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

    /**
     * 监听topic主题，消费消息
     * topics 配置主题，2.3以上版本可使用通配符
     * gropId 配置消费组
     *
     * @param record 消息
     */
    @KafkaListener(topics = "topic", groupId = "gaox")
    public void processOrder(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            Order order = JSON.parseObject(message.toString(), Order.class);
            log.info("kafka processOrder: {}", JSON.toJSONString(order));
        }
    }

}
