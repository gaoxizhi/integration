package net.gaox.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> kafka 配置 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:18
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "gaox.kafka")
public class KafkaConfig {

    /**
     * kafka 增量日志主题
     */
    private String topic;

}
