package net.gaox.livechatnetty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: <p>  </p>
 * @ClassName: ChatController
 * @Author: gaox·Eric
 * @Date: 2019/7/19 20:26
 */
@Controller
public class ChatController {
    @RequestMapping("/")
    public String WebsocketChatClient() {
        return "/WebsocketChatClient";
    }
}
