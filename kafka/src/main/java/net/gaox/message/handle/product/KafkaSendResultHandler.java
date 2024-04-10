package net.gaox.message.handle.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

/**
 * <p> Kafka消息发送回调 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-10 13:52
 */
@Slf4j
public class KafkaSendResultHandler implements ProducerListener<String, String> {

    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        log.info("消息发送成功：{}", producerRecord);
    }

    @Override
    public void onError(String topic, Integer partition, String key, String value, Exception exception) {
        log.info("消息发送失败: topic = {}, partition = {}, key = {}, value = {}.\n{}",
                topic, partition, key, value, exception.getMessage());
    }

}
