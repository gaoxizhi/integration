package net.gaox.livechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: <p>  </p>
 * @ClassName: ChatController
 * @author gaox·Eric
 * @date 2019/7/19 20:26
 */
@Controller
public class ChatController {
    @RequestMapping("/")
    public String webSocketChatClient() {
        return "/WebsocketChatClient";
    }
}
