package net.gaox.mqtt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> 配置类 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/27 11:02
 */
@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接名
     */
    private String host;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 默认 topic
     */
    private String topic;

    /**
     * 超时时间
     */
    private Long timeout;

}
