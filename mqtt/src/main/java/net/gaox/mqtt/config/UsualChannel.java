package net.gaox.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * <p> 普通订阅器 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/27 13:48
 */

@Configuration
@Slf4j
public class UsualChannel {

    private final MqttEnv vo;

    public UsualChannel(MqttEnv vo) {
        this.vo = vo;
    }

    /**
     * 全局消息订阅器
     */
    private static MqttPahoMessageDrivenChannelAdapter adapter;

    /**
     * 接收通道
     *
     * @return 直接通道
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }


    /**
     * 配置client,监听的topic
     *
     * @return
     */
    @Bean
    public MessageProducer inbound() {
        adapter = new MqttPahoMessageDrivenChannelAdapter(
                vo.getHost(), vo.getClientId() + "_usual", "aa", "/hello/1", "inTopic");
        adapter.setCompletionTimeout(vo.getTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * 通过通道获取数据
     *
     * @return 消息处理器
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String topic = message.getHeaders().get(ConstantInterface.RECEIVED_TOPIC).toString();
            String payload = message.getPayload().toString();
            if ("aa".equalsIgnoreCase(topic)) {
                System.out.printf("hello from: [aa], msg: [%s].", payload);
            } else if ("inTopic".equalsIgnoreCase(topic)) {
                System.out.printf("hello from: [inTopic], msg: [%s].\n", payload);
            }
            log.info("主题：[{}]，消息接收到的数据：[{}]", topic, payload);
        };
    }
}