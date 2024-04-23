package net.gaox.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import net.gaox.message.handle.product.KafkaSendResultHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * <p> Kafka 全局配置 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-10 15:13
 */
@Component
public class KafkaGlobalConfig {

    /**
     * 监听发送结果 处理器注入
     *
     * @return producerListener
     */
    @Bean("kafkaSendResultHandler")
    public ProducerListener kafkaSendResultHandler() {
        return new KafkaSendResultHandler();
    }

    /**
     * JSON序列化 用于将controller返回的实体类转换成json串
     */
    @Bean
    public FastJsonHttpMessageConverter jsonConverter() {

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteNullBooleanAsFalse
                , SerializerFeature.WriteNullNumberAsZero
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNonStringKeyAsString
                , SerializerFeature.DisableCircularReferenceDetect
                , SerializerFeature.WriteEnumUsingToString
        );
        converter.setFastJsonConfig(config);
        return converter;
    }

}
