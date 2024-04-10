package net.gaox.message.sender;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.conf.KafkaConfig;
import net.gaox.domain.model.entity.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p> Kafka 消息发送（配置开启事务） </p>
 *
 * @author gaox·Eric
 * @date 2024-04-09 21:31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionMessageSender {
    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 批量发送事务消息，需要开启事务
     *
     * @param orders 消息
     */
    public void sendMessageInTransaction(List<Order> orders) {
        kafkaTemplate.executeInTransaction(t -> {
            for (Order order : orders) {
                t.send(kafkaConfig.getTopic(), JSON.toJSONString(order));
            }
            return true;
        });
    }

}
