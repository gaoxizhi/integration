package net.gaox.livechat;

import net.gaox.livechat.support.TCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author gaox·Eric
 * @date 2019/7/19 22:09
 */
@SpringBootApplication
public class LiveChatApp {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(LiveChatApp.class, args);
        TCPServer tcpServer = context.getBean(TCPServer.class);
        tcpServer.start();
    }
}