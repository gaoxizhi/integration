package net.gaox.work.thread.v2;


/**
 * <p> 消息队列处理器 </p>
 * 从队列中不停取出 MethodMessage，然后执行 execute 方法
 *
 * @author gaox·Eric
 * @date 2024-01-31 12:50
 */
public class ActiveDaemonThread extends Thread {
    private final ActiveMessageQueue queue;

    public ActiveDaemonThread(ActiveMessageQueue queue) {
        super("activeDaemonThread");
        this.queue = queue;
        // ActiveMessageDaemonThread 为守护线程
        setDaemon(true);
    }

    @Override
    public void run() {
        for (; ; ) {
            // 从 ActiveMessage 队列中获取一个 ActiveMessage，然后执行 execute 方法
            ActiveMessage activeMessage = this.queue.take();
            activeMessage.execute();
        }
    }
}
