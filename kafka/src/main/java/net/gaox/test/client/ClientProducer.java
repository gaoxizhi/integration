package net.gaox.test.client;

import lombok.extern.slf4j.Slf4j;
import net.gaox.test.util.Constant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * <p> 发送测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-23 00:15
 */
@Slf4j
public class ClientProducer {

    private static final String TOPIC = Constant.TOPIC;

    public static void main(String[] args) {

        // 创建消息生产者
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(Constant.PRODUCER_PROPERTIES)) {
            for (int messageNo = 1; messageNo <= 100
                    ; messageNo++) {
                String msg = "你好，这是第" + messageNo + "条消息";
                String key = String.format("key-%03d", messageNo);
                ProducerRecord<String, String> record;

                int caseNumber = messageNo / 20;
                switch (caseNumber) {
                    // 不带key的消息
                    case 0:
                        producer.send(new ProducerRecord<>(TOPIC, msg));
                        break;
                    // 带key的消息
                    case 1:
                        producer.send(new ProducerRecord<>(TOPIC, key, msg));
                        break;
                    // 同步发送
                    case 2:
                        record = new ProducerRecord<>(TOPIC, key, msg);
                        try {
                            RecordMetadata result = producer.send(record).get();
                            log.info("发送消息成功: topic = {}, partition = {}, offset = {}.",
                                    result.topic(), result.partition(), result.offset());
                        } catch (Exception e) {
                            log.error("发送消息失败, data = {}.", msg, e);
                        }
                        break;
                    // 设置回调
                    default:
                        record = new ProducerRecord<>(TOPIC, key, msg);
                        producer.send(record, (result, e) -> {
                            if (e != null) {
                                log.warn("回调异常.", e);
                                return;
                            }
                            log.info("Coming in ProducerCallback: topic = {}, partition = {}, offset = {}.",
                                    result.topic(), result.partition(), result.offset());
                        });

                        producer.send(new ProducerRecord<>(TOPIC, msg));
                        break;
                }
            }
        }
    }

}
