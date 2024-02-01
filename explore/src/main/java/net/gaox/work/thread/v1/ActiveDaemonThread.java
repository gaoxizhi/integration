package net.gaox.work.thread.v1;

import net.gaox.work.thread.v1.message.MethodMessage;

/**
 * <p> 消息队列处理器 </p>
 * 从队列中不停取出 MethodMessage，然后执行 execute 方法
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:50
 */
public class ActiveDaemonThread extends Thread {
    private final MethodMessageQueue queue;

    public ActiveDaemonThread(MethodMessageQueue queue) {
        super("activeDaemonThread");
        this.queue = queue;
        // ActiveDaemonThread 为守护线程
        setDaemon(true);
    }

    @Override
    public void run() {
        for (; ; ) {
            // 从 MethodMessage 队列中获取一个 MethodMessage，然后执行 execute 方法
            MethodMessage methodMessage = this.queue.take();
            methodMessage.execute();
        }
    }

}
