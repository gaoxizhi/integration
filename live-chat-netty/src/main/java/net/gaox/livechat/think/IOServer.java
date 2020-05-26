package net.gaox.livechat.think;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p> 传统IO模型 聊天服务端 </p>
 * 当获取到新的连接之后，给每条连接创建一个新的线程，这个线程负责从该连接中读取数据
 *
 * @author gaox·Eric
 * @date 2020/5/23 23:27
 */
@Slf4j
public class IOServer {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);
        // (1) 接收新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();

                            // (3) 按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                log.error(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                        }
                    }).start();

                } catch (IOException e) {
                }
            }
        }).start();
    }
}
