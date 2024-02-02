package net.gaox.message.driven.event.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.gaox.message.driven.event.Event;

/**
 * <p> 输入消息，定义了两个属性 X 和 Y </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:26
 */
@Getter
@AllArgsConstructor
public class InputEvent extends Event {
    private final int x;
    private final int y;
}