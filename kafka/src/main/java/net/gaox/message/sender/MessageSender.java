package net.gaox.message.sender;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.gaox.conf.KafkaConfig;
import net.gaox.domain.model.entity.Order;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> Kafka 消息发送（非事务） </p>
 *
 * @author gaox·Eric
 * @date 2024-04-09 21:31
 */
@Slf4j
@Component
public class MessageSender {
    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 构造一个 无事务管理的 KafkaTemplate
     *
     * @param kafkaConfig     kafka 配置
     * @param producerFactory 生产者工厂
     */
    public MessageSender(KafkaConfig kafkaConfig, ProducerFactory<String, String> producerFactory) {
        this.kafkaConfig = kafkaConfig;

        // 创建一个新的 producer factory，不设置 transaction.id.prefix
        Map<String, Object> properties = ((DefaultKafkaProducerFactory<?, ?>) producerFactory)
                .getConfigurationProperties();
        Map<String, Object> configProps = new HashMap<>(properties);
        configProps.remove(ProducerConfig.TRANSACTIONAL_ID_CONFIG);

        // 创建一个新的 producer factory 用于非事务性的 KafkaTemplate
        DefaultKafkaProducerFactory<String, String> nonTransactionalProducerFactory =
                new DefaultKafkaProducerFactory<>(configProps);

        // 创建非事务性的 KafkaTemplate
        this.kafkaTemplate = new KafkaTemplate<>(nonTransactionalProducerFactory);
    }


    /**
     * 发送消息
     *
     * @param order 订单
     */
    public void sender(Order order) {
        kafkaTemplate.send(kafkaConfig.getTopic(), JSON.toJSONString(order));
    }

    /**
     * 根据订单号发送消息
     * 受限于 本项目start版本较低，暂时不支持通配符消费，所以这里只能发送指定topic
     *
     * @param order 订单
     */
    public void senderTopicByOrderNumber(Order order) {
        kafkaTemplate.send(kafkaConfig.getTopic() + "." + order.getId(), JSON.toJSONString(order));
    }

}
