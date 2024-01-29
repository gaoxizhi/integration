package net.gaox.thread.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * <p> 处理与客户端的通信交互 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-29 15:20
 */
@Slf4j
public class ClientHandler implements Runnable {

    /**
     * 客户端的 socket 连接
     */
    private final Socket socket;

    /**
     * 客户端的 identity
     */
    private final String clientIdentify;

    /**
     * 通过客户端连接的构造函数
     *
     * @param socket 客户端连接
     */
    public ClientHandler(final Socket socket) {
        this.socket = socket;
        this.clientIdentify = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    /**
     * 与客户端进行简单通信交互的线程任务
     */
    @Override
    public void run() {
        try {
            this.chat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chat() throws IOException {
        BufferedReader bufferedReader = wrap2Reader(this.socket.getInputStream());
        PrintStream printStream = wrap2Print(this.socket.getOutputStream());
        String received;
        while ((received = bufferedReader.readLine()) != null) {
            // 将客户端发送的消息输出到控制台
            log.info("client:[{}]-message:[{}]", clientIdentify, received);
            // 如果客户端发送了 quit 指令，则断开与客户端的连接
            if ("quit".equals(received)) {
                // 向客户端发送关闭消息
                write2Client(printStream, "client will close");
                socket.close();
                break;
            }
            // 向客户端发送消息
            write2Client(printStream, "Server:" + received);
        }
    }

    /**
     * 将输入字节流封装成 BufferedReader 缓冲字符流
     *
     * @param inputStream 输入流
     * @return buff
     */
    private BufferedReader wrap2Reader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * 将输出字节流封装成 PrintStream
     *
     * @param outputStream 输出流
     * @return 网络输出流
     */
    private PrintStream wrap2Print(OutputStream outputStream) {
        return new PrintStream(outputStream);
    }

    /**
     * 向客户端发送消息
     *
     * @param print   网络输出流
     * @param message 消息
     */
    private void write2Client(PrintStream print, String message) {
        print.println(message);
        print.flush();
    }

}
