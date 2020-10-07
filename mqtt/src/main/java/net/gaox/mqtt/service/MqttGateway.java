package net.gaox.mqtt.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * <p> 消息推送接口 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/27 09:19
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
    /**
     * 发送消息到Mqtt服务器
     *
     * @param data 消息
     * @param topic 主题
     */
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}