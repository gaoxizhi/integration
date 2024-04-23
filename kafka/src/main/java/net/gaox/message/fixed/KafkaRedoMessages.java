package net.gaox.message.fixed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * <p> 修复异常，在新的消费者组重新消费 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-23 09:15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaRedoMessages {

    private final KafkaProperties kafkaProperties;

    /**
     * 重做的 组id
     */
    private static String GROUP_ID = "redo-group";

    private KafkaConsumer<String, String> consumer;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumer = new KafkaConsumer<>(props);
    }

    /**
     * 重新消费指定时间区间的消息
     * KafkaConsumer 不是线程安全的 在此处加上同步锁
     *
     * @param topics    topic列表
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public synchronized void redoMessages(List<String> topics, LocalDateTime startTime, LocalDateTime endTime) {
        // 将时间转换为 Epoch 毫秒
        long startEpochMillis = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endEpochMillis = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 订阅指定 topic
        consumer.subscribe(topics);

        // 获取当前消费者组所消费的分区
        Set<TopicPartition> partitions = consumer.assignment();

        // 构建 Map，包含要查询的分区和对应的时间戳
        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();

        // 设置每个分区最早消息的时间
        for (TopicPartition partition : partitions) {
            timestampsToSearch.put(partition, startEpochMillis);
        }

        // 获取指定时间点分区的偏移量
        Map<TopicPartition, OffsetAndTimestamp> offsetAndTimestampMap = consumer.offsetsForTimes(timestampsToSearch);

        // 处理查询结果
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : offsetAndTimestampMap.entrySet()) {
            TopicPartition partition = entry.getKey();
            long currentPosition = consumer.position(partition);
            log.info("topic = {}, partition = {}, current position = {}",
                    partition.topic(), partition.partition(), currentPosition);
            OffsetAndTimestamp offsetAndTimestamp = entry.getValue();
            // 如果有值，说明找到了指定时间点后的偏移量
            if (offsetAndTimestamp != null) {
                long offset = offsetAndTimestamp.offset();
                log.info("topic = {}, partition = {}, first offset = {}",
                        partition.topic(), partition.partition(), offset);
                consumer.seek(partition, offset);
            }
        }

        // 重新消费消息并进行处理，直到超出指定时间范围
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (null == records || records.isEmpty()) {
                return;
            }
            for (ConsumerRecord<String, String> record : records) {
                // 获取消息的时间戳
                long messageTimestamp = record.timestamp();

                // 检查消息的时间是否在指定的范围内
                if (startEpochMillis > messageTimestamp || endEpochMillis < messageTimestamp) {
                    // 超出指定时间范围，退出循环
                    return;
                }

                // 处理指定时间范围内的消息
                processMessage(record);
            }
        }
    }

    private void processMessage(ConsumerRecord<String, String> record) {
        // 处理消息的业务逻辑
        log.info("Redoing message: topic = {}, partition = {}, offset = {}, timestamp = {}, " +
                        "key = {}, value = {}, headers = {}, ",
                record.topic(), record.partition(), record.offset(),
                Instant.ofEpochMilli(record.timestamp()).atZone(ZoneId.systemDefault()).toLocalDateTime(),
                record.key(), record.value(), record.headers());
    }

}
