package net.gaox.message.driven.test.async;

import net.gaox.message.driven.test.async.handler.AsyncInputEventHandler;
import net.gaox.message.driven.test.async.handler.AsyncResultEventHandler;
import net.gaox.message.driven.async.router.AsyncEventDispatcher;
import net.gaox.message.driven.event.entity.InputEvent;
import net.gaox.message.driven.event.entity.ResultEvent;

import java.util.concurrent.TimeUnit;

/**
 * <p> 异步事件驱动设计 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:23
 */
public class AsyncEventDispatcherExample {

    public static void main(String[] args) throws InterruptedException {
        AsyncEventDispatcher dispatcher = new AsyncEventDispatcher();
        dispatcher.registerChannel(InputEvent.class, new AsyncInputEventHandler(dispatcher));
        dispatcher.registerChannel(ResultEvent.class, new AsyncResultEventHandler());
        dispatcher.dispatch(new InputEvent(1, 2));
        TimeUnit.SECONDS.sleep(2);
        dispatcher.dispatch(new InputEvent(3, 7));
    }

}

