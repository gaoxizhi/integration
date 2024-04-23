package net.gaox.test.client;

import lombok.extern.slf4j.Slf4j;
import net.gaox.test.util.Constant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p> 消费测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-23 00:20
 */
@Slf4j
public class ClientConsumer {

    private static KafkaConsumer<String, String> consumer;

    public static void main(String[] args) {
        consumer = new KafkaConsumer<>(Constant.CONSUMER_PROPERTIES);

        // 订阅主题
        consumer.subscribe(Collections.singletonList(Constant.TOPIC));

        log.info("---------开始消费---------");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        // 主动拉取消息，1秒拉取一次
        Runnable periodicTask = () -> {
            ConsumerRecords<String, String> msgList = consumer.poll(Duration.ofMillis(100));
            if (null != msgList && msgList.count() > 0) {
                // 遍历取得的消息
                for (ConsumerRecord<String, String> record : msgList) {
                    log.info("收到消息: topic = {}, key = {}, partition = {}, offset = {}, value = {}.",
                            record.topic(), record.key(), record.partition(), record.offset(), record.value());
                }
            }
        };
        executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS);

        // 安排5分钟后关闭线程池
        executor.schedule(executor::shutdown, 1, TimeUnit.MINUTES);

        // 启动一个线程，用于监听控制台输入，如果输入“exit”，则关闭流
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals(Constant.EXIT_KEY)) {
                if (consumer != null) {
                    // 取消订阅
                    consumer.unsubscribe();
                    // 关闭消费者
                    consumer.close();
                }
                System.exit(0);
            }
        }, "consumer-exit").start();
    }

}
