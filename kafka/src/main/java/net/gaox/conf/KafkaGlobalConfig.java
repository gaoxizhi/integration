package net.gaox.conf;

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

}
