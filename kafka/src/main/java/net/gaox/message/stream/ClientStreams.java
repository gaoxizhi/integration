package net.gaox.message.stream;

import lombok.extern.slf4j.Slf4j;
import net.gaox.test.util.Constant;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * <p> 流处理器 流转换 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-23 00:03
 */
@Slf4j
@Component
@EnableKafkaStreams
public class ClientStreams {

    /**
     * 通过自动注入的StreamBuilder来创建KStream
     *
     * @param builder 流构造器
     * @return 转换流
     */
    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {

        // 配置流处理器的输入和输出类型，这样可以避免类似的类型转换异常或null异常
        Consumed<String, String> consumeSer = Consumed.with(Serdes.String(), Serdes.String());
        Produced<String, String> produceSer = Produced.with(Serdes.String(), Serdes.String());

        KStream<String, String> stream = builder.stream(Constant.TOPIC, consumeSer);
        // 设置对消息进行处理的处理器
        stream.flatMapValues((ValueMapper<String, Iterable<String>>) Arrays::asList)
                // to()方法指定目标主题
                .to(Constant.SINK_TOPIC, produceSer);
        // 创建Topology对象
        log.info("流的拓扑关系: {}", builder.build().describe());
        // 直接返回KStream就行了
        return stream;
    }

}
