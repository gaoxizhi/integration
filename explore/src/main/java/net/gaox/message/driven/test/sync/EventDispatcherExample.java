package net.gaox.message.driven.test.sync;

import lombok.extern.slf4j.Slf4j;
import net.gaox.message.driven.event.entity.InputEvent;
import net.gaox.message.driven.event.entity.ResultEvent;
import net.gaox.message.driven.sync.router.EventDispatcher;
import net.gaox.message.driven.test.sync.handler.InputEventHandler;
import net.gaox.message.driven.test.sync.handler.ResultEventHandler;

import java.util.concurrent.TimeUnit;

/**
 * <p> 同步事件驱动设计 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 09:29
 */
@Slf4j
public class EventDispatcherExample {

    public static void main(String[] args) throws InterruptedException {
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.registerChannel(InputEvent.class, new InputEventHandler(dispatcher));
        dispatcher.registerChannel(ResultEvent.class, new ResultEventHandler());
        dispatcher.dispatch(new InputEvent(1, 2));
        TimeUnit.SECONDS.sleep(2);
        dispatcher.dispatch(new InputEvent(3, 7));

    }

}
