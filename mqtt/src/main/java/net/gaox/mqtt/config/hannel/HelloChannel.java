package net.gaox.mqtt.config.hannel;

import lombok.extern.slf4j.Slf4j;
import net.gaox.mqtt.config.constant.Constant;
import net.gaox.mqtt.config.properties.MqttProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * <p> 订阅消息通道2 温湿度信息 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/27 10:42
 */

@Configuration
@Slf4j
public class HelloChannel {

    private final MqttProperties vo;

    private final MqttPahoClientFactory factory;

    public HelloChannel(MqttProperties vo, MqttPahoClientFactory factory) {
        this.vo = vo;
        this.factory = factory;
    }

    /**
     * 全局消息订阅器
     */
    private static MqttPahoMessageDrivenChannelAdapter adapter;

    /**
     * 通道2
     *
     * @return 直接通道
     */
    @Bean
    public MessageChannel mqttInputChannelSoma() {
        return new DirectChannel();
    }

    /**
     * 配置订阅消息，对本系统相当于生产者
     * 监听的topic: /hello/#
     * 所有以{/hello/}开头的消息
     *
     * @return 消息生产者
     */
    @Bean
    public MessageProducer inboundSoma() {

        adapter = new MqttPahoMessageDrivenChannelAdapter(vo.getClientId() + "_hello", factory, "/hello/#");
        adapter.addTopic("gao");
        adapter.removeTopic("gao");
        adapter.setCompletionTimeout(vo.getTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannelSoma());
        return adapter;
    }

    /**
     * 通过通道2获取数据
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannelSoma")
    public MessageHandler handlerHello() {
        return message -> {
            String topic = message.getHeaders().get(Constant.RECEIVED_TOPIC).toString();
            String type = topic.substring(topic.lastIndexOf("/") + 1);
            String payload = message.getPayload().toString();
            //处理指定消息集合
            if ("/hello/iot".equalsIgnoreCase(topic)) {
                System.out.printf("hello from: [%s], msg: [%s]，开机！\n", type, payload);
            }
            log.info("主题：[{}]，消息接收到的数据：[{}]", topic, payload);
        };
    }
}