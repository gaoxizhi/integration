package net.gaox.work.thread.v2;

import java.util.LinkedList;

/**
 * <p> 消息队列 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:36
 */
public class ActiveMessageQueue {

    /**
     * 用于存放提交的 ActiveMessage 消息
     */
    private final LinkedList<ActiveMessage> messages = new LinkedList<>();

    public ActiveMessageQueue() {
        // 启动 Worker 线程
        new ActiveDaemonThread(this).start();
    }

    public void offer(ActiveMessage activeMessage) {
        synchronized (this) {
            messages.addLast(activeMessage);
            // 因为只有一个线程负责 take 数据，因此没有必要使用 notifyAll 方法
            this.notify();
        }
    }

    public ActiveMessage take() {
        synchronized (this) {
            // 当 ActiveMessage 队列中没有 Message 的时候，执行线程进入阻塞
            while (messages.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 获取其中一个 ActiveMessage 并且从队列中移除
            return messages.removeFirst();
        }
    }

}
