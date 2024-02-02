package net.gaox.message.driven.test.async.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.message.driven.async.channel.AsyncChannel;
import net.gaox.message.driven.async.router.AsyncEventDispatcher;
import net.gaox.message.driven.event.Event;
import net.gaox.message.driven.event.entity.InputEvent;
import net.gaox.message.driven.event.entity.ResultEvent;

import java.util.concurrent.TimeUnit;

/**
 * <p> 输入消息的处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:31
 */
@Slf4j
@AllArgsConstructor
public class AsyncInputEventHandler extends AsyncChannel {

    /**
     * 事件调度器
     */
    private final AsyncEventDispatcher dispatcher;

    @Override
    protected void handle(Event message) {
        InputEvent inputEvent = (InputEvent) message;
        log.info("X: {}, Y: {}", inputEvent.getX(), inputEvent.getY());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result = inputEvent.getX() + inputEvent.getY();
        log.info("calculate the result is: {}", result);
        dispatcher.dispatch(new ResultEvent(result));
    }

}
