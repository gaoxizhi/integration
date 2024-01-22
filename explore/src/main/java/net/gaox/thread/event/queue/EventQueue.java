package net.gaox.thread.event.queue;

import java.util.LinkedList;

import static java.lang.Thread.currentThread;

/**
 * <p> 事件队列 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-22 11:11
 */
public class EventQueue {

    private final int max;

    private final static int DEFAULT_MAX_EVENT = 10;

    /**
     * 在 EventQueue 中定义了一个队列
     */
    private final LinkedList<Event> eventQueue = new LinkedList<>();

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueue(int max) {
        this.max = max;
    }

    /**
     * offer方法会提交一个Event至队尾
     * 如果此时队列已经满了，那么提交的线程将会被阻塞
     *
     * @param event
     */
    public void offer(Event event) {
        synchronized (eventQueue) {
            // 临界值的判断 if 更改为 while
            while (eventQueue.size() >= max) {
                try {
                    console(" the queue is full.");
                    // 调用 wait 方法 阻塞线程
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            console(" the new event is submitted");
            eventQueue.addLast(event);
            eventQueue.notifyAll();
        }
    }

    /**
     * 从队头获取数据
     * 如果队列中没有可用数据，那么工作线程就会被阻塞
     *
     * @return
     */
    public Event take() {
        synchronized (eventQueue) {
            while (eventQueue.isEmpty()) {
                try {
                    console(" the queue is empty.");
                    // 调用 wait 方法 阻塞线程
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Event event = eventQueue.removeFirst();
            // notify 方法的作用是唤醒那些曾经执行 monitor 的 wait 方法而进入阻塞的一个线程
            // notifyAll，会唤醒在 wait set 中休息的所有线程
            this.eventQueue.notifyAll();
            console(" the event " + event + " is handled.");
            return event;
        }
    }

    private void console(String message) {
        System.out.printf("%s:%s\n", currentThread().getName(), message);
    }

}
