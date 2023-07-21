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

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * <p> 普通订阅器 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/27 13:48
 */

@Slf4j
@Configuration
public class UsualChannel {

    private final MqttProperties vo;
    private final MqttPahoClientFactory factory;

    public UsualChannel(MqttProperties vo, MqttPahoClientFactory factory) {
        this.vo = vo;
        this.factory = factory;
    }

    /**
     * 全局消息订阅器
     */
    private static MqttPahoMessageDrivenChannelAdapter adapter;

    /**
     * 主题列表，可通过add、remove方法动态调整
     */
    private static HashSet<String> topics = new HashSet<>();

    static {
        topics.add("inTopic");
    }

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
        adapter = new MqttPahoMessageDrivenChannelAdapter(vo.getClientId() + "_usual", factory, "aa");
        topics.stream().forEach(s -> adapter.addTopic(s, 1));
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
            String topic = message.getHeaders().get(Constant.RECEIVED_TOPIC).toString();
            String payload = message.getPayload().toString();
            if ("aa".equalsIgnoreCase(topic)) {
                System.out.printf("hello from: [aa], msg: [%s].", payload);
            } else if ("inTopic".equalsIgnoreCase(topic)) {
                System.out.printf("hello from: [inTopic], msg: [%s].\n", payload);
            }
            log.info("主题：[{}]，消息接收到的数据：[{}]", topic, payload);
        };
    }

    /**
     * 动态添加订阅主题
     *
     * @param topic 主题s
     */
    public void addListenTopic(String... topic) {
        if (null == adapter) {
            inbound();
        }
        log.debug("UsualChannel sub topic before are [{}]",
                Arrays.asList(adapter.getTopic()).stream().collect(Collectors.joining(", ")));
        adapter.addTopic(topic);
        log.debug("UsualChannel sub topic now are [{}]",
                Arrays.asList(adapter.getTopic()).stream().collect(Collectors.joining(", ")));
    }

    /**
     * 动态移除订阅主题
     *
     * @param topic 主题s
     */
    public void removeListenTopic(String... topic) {
        if (null == adapter) {
            inbound();
        }
        log.debug("UsualChannel sub topic before are [{}]",
                Arrays.asList(adapter.getTopic()).stream().collect(Collectors.joining(", ")));
        adapter.removeTopic(topic);
        log.debug("UsualChannel sub topic now are [{}]",
                Arrays.asList(adapter.getTopic()).stream().collect(Collectors.joining(", ")));
    }
}