package net.gaox.message.eda;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p> 简单事件驱动模式 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 08:05
 */
public class EventDrivenExample {

    public static void main(String[] args) {
        Queue<Event> events = new LinkedList<>();
        events.add(new Event("A", "Hello"));
        events.add(new Event("A", "I am Event A"));
        events.add(new Event("B", "I am Event B"));
        events.add(new Event("B", "World"));

        Event e;
        while (!events.isEmpty()) {
            // 从消息队列中不断移除 Event，根据不同的类型进行处理
            e = events.remove();
            switch (e.getType()) {
                case "A":
                    EventHandlers.handleEventA(e);
                    break;
                case "B":
                    EventHandlers.handleEventB(e);
                    break;
            }
        }

    }
}
