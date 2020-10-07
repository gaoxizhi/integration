package net.gaox.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * <p> 配置类 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/27 11:02
 */
@Data
@Primary
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttEnv {
    private String username;
    private String password;
    private String host;
    private String clientId;
    private String topic;
    private Long timeout;
}
