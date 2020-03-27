package net.gaox.mqtt;

import net.gaox.mqtt.config.MqttEnv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p> 启动类 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/25 16:50
 */
@SpringBootApplication
@EnableConfigurationProperties({MqttEnv.class})
public class MqttApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqttApplication.class, args);
    }
}