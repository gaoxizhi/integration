package net.gaox.mqtt.controller;

import net.gaox.mqtt.service.MqttGateway;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p> 测试类 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/27 09:22
 */
@RestController
@RequestMapping("/mqtt")
public class TestController {

    @Resource
    private MqttGateway mqttGateway;

    @Resource
    private MqttPahoMessageHandler mqttHandler;

    @GetMapping("/send")
    public String sendMqtt(@RequestParam String topic, @RequestParam String content) {
        mqttGateway.sendToMqtt(content, topic);
        return "set OK";
    }

    @GetMapping("/send1")
    public String send(@RequestParam String topic, @RequestParam String content) {
        // 构建消息
        Message<String> messages = MessageBuilder.withPayload(content).setHeader(MqttHeaders.TOPIC, topic).build();
        // 发送消息
        mqttHandler.handleMessage(messages);
        return "ok";
    }
}