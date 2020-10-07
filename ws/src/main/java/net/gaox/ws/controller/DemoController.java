package net.gaox.ws.controller;

import lombok.extern.slf4j.Slf4j;
import net.gaox.ws.scoket.WsServer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 接口测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/1 00:21
 */
@RestController
@Slf4j
public class DemoController {

    @GetMapping("demo")
    @ResponseBody
    public String demo() {
        return "this is a demo~";
    }

    @RequestMapping("/push/{id}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String id) {
        WsServer.pushMessage(message, id);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }
}