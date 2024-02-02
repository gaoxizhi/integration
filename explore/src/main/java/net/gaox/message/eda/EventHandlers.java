package net.gaox.message.eda;

/**
 * <p> Event 处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 19:33
 */
public class EventHandlers {

    /**
     * handleEventA 方法只是简单地将 Event 中的 data 进行了 lowerCase 之后的输出
     *
     * @param e 事件对象
     */
    public static void handleEventA(Event e) {

        System.out.println(e.getData().toLowerCase());

    }

    public static void handleEventB(Event e) {
        System.out.println(e.getData().toUpperCase());
    }


}
