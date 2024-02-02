package net.gaox.message.driven.event.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.gaox.message.driven.event.Event;

/**
 * <p> 结果消息，定义了一个属性 result </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:26
 */
@Getter
@AllArgsConstructor
public class ResultEvent extends Event {
    private final int result;
}