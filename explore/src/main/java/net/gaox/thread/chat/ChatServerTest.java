package net.gaox.thread.chat;

import java.io.IOException;

/**
 * <p> 聊天服务器测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 15:55
 */
public class ChatServerTest {
    public static void main(String[] args) throws IOException {
        new ChatServer(13312).startServer();
    }
}
