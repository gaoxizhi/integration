package net.gaox.message.eda;

import lombok.Getter;

/**
 * <p> 事件数据 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-01 19:30
 */
@Getter
public class Event {

    /**
     * 事件类型
     */
    private final String type;

    /**
     * 事件数据
     */
    private final String data;

    public Event(String type, String data) {
        this.type = type;
        this.data = data;
    }

}
