package net.gaox.test.client.stream;

import lombok.extern.slf4j.Slf4j;
import net.gaox.test.util.Constant;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * <p> 流处理器 流转换 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-23 00:03
 */
@Slf4j
public class ClientStreams {
    private static KafkaStreams streams;

    public static void main(String[] args) {

        // 创建StreamsBuilder
        final StreamsBuilder builder = new StreamsBuilder();
        // stream()方法指定源主题
        builder.<String, String>stream(Constant.TOPIC)
                // 设置对消息进行处理
                .mapValues(value -> "get: key number = " + value)
                // 设置对消息进行处理的处理器（可换成Lambda表达式）
                .flatMapValues((ValueMapper<String, Iterable<String>>) value -> Arrays.asList(value.split("\\W+")))
                // to()方法指定目标主题
                .to(Constant.SINK_TOPIC);
        // 创建Topology对象
        final Topology topology = builder.build();
        // 输出Topology对象代表的流关系
        System.out.println(topology.describe());
        // 创建KafkaStreams实例
        streams = new KafkaStreams(topology, Constant.STREAM_PROPERTIES);

        // 开始执行“导流”
        streams.start();

        // 启动一个线程，用于监听控制台输入，如果输入“exit”，则关闭流
        new Thread(() -> {
            long start = System.currentTimeMillis();
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals(Constant.EXIT_KEY)) {
                if (streams != null) {
                    // 关闭流
                    streams.close();
                }
                System.exit(0);
            }
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    log.warn("定时退出线程异常中断");
                }
                if (System.currentTimeMillis() - start > TimeUnit.MINUTES.toMillis(1)) {
                    if (streams != null) {
                        // 关闭流
                        streams.close();
                    }
                    System.exit(0);
                }
            }
        }, "stream-exit").start();
    }

}
