package net.gaox.thread.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * <p> 监控客户端 socket 连接的 hook 线程，关闭时清理客户端连接资源 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 10:02
 */
@Slf4j
public class SocketHookThread extends Thread {

    /**
     * 客户端的 socket 连接
     */
    private final Socket socket;


    public SocketHookThread(String threadName, Socket socket) {
        super(threadName);
        this.socket = socket;
    }

    @Override
    public void run() {
        // 当前线程作为守护线程运行，监控主线程状态
        while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
            try {
                // 每秒检查一次，可根据实际需求调整频率
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // 线程中断，准备清理资源并退出
                break;
            }
        }

        // 清理资源
        if (!socket.isClosed()) {
            try {
                socket.close();
                log.warn("Socket closed by hook thread.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在需要的地方启动这个守护线程
     *
     * @param threadName 线程名
     * @param socket     socket 连接
     */
    public static void startHookThread(String threadName, Socket socket) {
        new SocketHookThread("hook-thread-" + threadName, socket).start();
    }

}
