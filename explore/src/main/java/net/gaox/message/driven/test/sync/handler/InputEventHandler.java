package net.gaox.message.driven.test.sync.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.message.driven.event.entity.InputEvent;
import net.gaox.message.driven.event.entity.ResultEvent;
import net.gaox.message.driven.channel.Channel;
import net.gaox.message.driven.sync.router.EventDispatcher;

/**
 * <p> 输入消息的处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:31
 */
@Slf4j
@AllArgsConstructor
public class InputEventHandler implements Channel<InputEvent> {

    /**
     * 事件调度器
     */
    private final EventDispatcher dispatcher;

    @Override
    public void dispatch(InputEvent message) {
        log.info("X: {}, Y: {}", message.getX(), message.getY());
        int result = message.getX() + message.getY();
        log.info("calculate the result is: {}", result);
        dispatcher.dispatch(new ResultEvent(result));
    }

}
