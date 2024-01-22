package net.gaox.thread.event.queue;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p> 事件队列操作 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-22 11:11
 */
public class EventClient {

    public static void main(String[] args) {
        final EventQueue eventQueue = new EventQueue();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (; ; ) {
                    eventQueue.offer(new Event());
                }
            }, "Producer").start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (; ; ) {
                    eventQueue.take();
                    try {
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt() % 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Consumer").start();
        }
    }

}
