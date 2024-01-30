package net.gaox.thread.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.net.Socket;

/**
 * <p> socket关闭清理跟踪器 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 17:01
 */
@Slf4j
public class SocketCleaningTracker {

    private static final ReferenceQueue<Object> queue = new ReferenceQueue<>();

    static {
        // 静态方法 启动 Cleaner 线程
        log.info("start SocketCleaningTracker Cleaner thread");
        new Cleaner().start();
    }

    public static void track(Socket socket) {
        log.info("track socket: {}", socket);
        new Tracker(socket, queue);
    }

    /**
     * socket 关闭清理跟踪器 继承虚引用
     */
    private static final class Tracker extends PhantomReference<Object> {
        private final Socket socket;

        Tracker(Socket socket, ReferenceQueue<? super Object> queue) {
            super(socket, queue);
            this.socket = socket;
        }

        public void close() {
            try {
                log.info("close socket: {}", socket);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * socket 关闭清理线程静态内部类
     */
    private static class Cleaner extends Thread {
        private Cleaner() {
            super("socketCleaningTracker");
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 当 Tracker 被垃圾回收器回收时会加入 Queue 中
                    Tracker tracker = (Tracker) queue.remove();
                    log.info("socketCleaningTracker Cleaner thread get Tracker: {}", tracker);
                    tracker.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
