package net.gaox.test.util;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/**
 * <p> 测试常量 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-23 01:12
 */
public class Constant {

    /**
     * 生产&消费的主题
     */
    public final static String TOPIC = "gaox-client";

    /**
     * 流转发主题
     */
    public final static String SINK_TOPIC = "gaox-stream";

    /**
     * 消费者实例所属的组ID
     */
    public static final String GROUP_ID = "group-gaox";

    /**
     * 退出命令
     */
    public static final String EXIT_KEY = ":exit";

    /**
     * 消费者配置
     */
    public static Properties CONSUMER_PROPERTIES = new Properties();

    static {
        // 指定Kafka的节点地址
        CONSUMER_PROPERTIES.put("bootstrap.servers", "localhost:9092");
        // 指定消费者组ID
        CONSUMER_PROPERTIES.put("group.id", Constant.GROUP_ID);
        // 设置是否自动提交offset
        CONSUMER_PROPERTIES.put("enable.auto.commit", "true");
        // 设置自动提交offset的时间间隔
        CONSUMER_PROPERTIES.put("auto.commit.interval.ms", "1000");
        // session超时时长
        CONSUMER_PROPERTIES.put("session.timeout.ms", "30000");
        // 程序读取消息的初始offset
        CONSUMER_PROPERTIES.put("auto.offset.reset", "latest");
        // 指定消息key的反序列化器
        CONSUMER_PROPERTIES.put("key.deserializer", StringDeserializer.class.getName());
        // 指定消息value的反序列化器
        CONSUMER_PROPERTIES.put("value.deserializer", StringDeserializer.class.getName());
    }

    /**
     * 生产者配置
     */
    public static Properties PRODUCER_PROPERTIES = new Properties();

    static {
        // 指定Kafka的节点地址
        PRODUCER_PROPERTIES.put("bootstrap.servers", "localhost:9092");
        // 指定确认机制，默认值是0。
        PRODUCER_PROPERTIES.put("acks", "all");
        // 指定发送失败后的重试次数
        PRODUCER_PROPERTIES.put("retries", 0);
        // 当多条消息要发送到同一分区时，生产者将尝试对多条消息进行批处理，
        // 从而减少网络请求数，这有助于提高客户机和服务器的性能。
        // 该参数控制默认的批处理的数据大小
        PRODUCER_PROPERTIES.put("batch.size", 16384);
        // 指定消息key的序列化器
        PRODUCER_PROPERTIES.put("key.serializer", StringSerializer.class.getName());
        // 指定消息value的序列化器
        PRODUCER_PROPERTIES.put("value.serializer", StringSerializer.class.getName());
    }

    /**
     * 流转发配置
     */
    public static Properties STREAM_PROPERTIES = new Properties();

    static {
        // 程序的唯一标识符，用于区别于其他应用程序与同一Kafka集群通信
        STREAM_PROPERTIES.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        // 指定Kafka的节点地址
        STREAM_PROPERTIES.put("bootstrap.servers", "localhost:9092");
        // 指定消息key默认的序列化和反序列化器
        STREAM_PROPERTIES.put("default.key.serde", Serdes.String().getClass());
        // 指定消息value默认的序列化和反序列化器
        STREAM_PROPERTIES.put("default.value.serde", Serdes.String().getClass());
    }

}
