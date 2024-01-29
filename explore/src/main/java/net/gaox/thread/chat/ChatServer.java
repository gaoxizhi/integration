package net.gaox.thread.chat;

import lombok.extern.slf4j.Slf4j;
import net.gaox.thread.gaoxpool.GaoxPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p> 聊天服务器 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 15:03
 */
@Slf4j
public class ChatServer {

    /**
     * 服务端端口
     */
    private final int port;

    /**
     * 线程池
     */
    private GaoxPool threadPool;

    /**
     * 服务端套接字
     */
    private ServerSocket serverSocket;

    /**
     * 指定端口的聊天服务器的构造方法
     *
     * @param port 聊天服务端口
     */
    public ChatServer(int port) {
        this.port = port;
    }

    /**
     * 构造默认端口 13312 的聊天服务器
     */
    public ChatServer() {
        this(13312);
    }

    /**
     * 启动聊天服务器
     *
     * @throws IOException ioe
     */
    public void startServer() throws IOException {
        // 创建线程池，初始化一个线程，核心线程数量为2，最大线程数为4，阻塞队列中最大可加入1000个任务
        this.threadPool = new GaoxPool(1, 2, 4, 1000);
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
        log.info("Chat server is started and listen at port: {}", port);
        this.listen();
    }

    /**
     * 监听客户端连接
     * 当接收到了新的客户端连接时，会为每一个客户端连接创建一个线程 ClientHandler 与客户端进行交互
     * 当客户端的连接个数超过线程池的最大数量时，客户端虽然可以成功接入服务端，但是会进入阻塞队列。
     *
     * @throws IOException ioe
     */
    private void listen() throws IOException {
        while (true) {
            // accept 方法是阻塞方法，当有新的链接进入时才会返回，并且返回的是客户端的连接
            Socket client = serverSocket.accept();
            log.info("The client [{}] connected.", client);
            // 将客户端连接作为一个 Request 封装成对应的 Handler 然后提交给线程池
            this.threadPool.execute(new ClientHandler(client));
        }
    }
}
