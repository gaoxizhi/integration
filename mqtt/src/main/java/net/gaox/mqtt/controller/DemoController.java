package net.gaox.mqtt.controller;

import lombok.extern.slf4j.Slf4j;
import net.gaox.mqtt.config.UsualChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p> 接口测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/1 00:21
 */
@RestController
@Slf4j
public class DemoController {

    @Resource
    private UsualChannel usualChannel;


    @GetMapping("demo")
    @ResponseBody
    public String demo() {
        return "this is a demo~";
    }

    @GetMapping("/mqtt/addTopic")
    public String addTopic(String[] topic) {
        usualChannel.addListenTopic(topic);
        log.info("Add topic to UsualChannel. topic are : [{}]",
                Arrays.asList(topic).stream().collect(Collectors.joining(", ")));
        return "OK";
    }

    @GetMapping("/mqtt/removeTopic")
    public String removeTopic(String[] topic) {
        usualChannel.removeListenTopic(topic);
        log.info("Remove topic to UsualChannel. topic are : [{}]",
                Arrays.asList(topic).stream().collect(Collectors.joining(", ")));
        return "OK";
    }
}