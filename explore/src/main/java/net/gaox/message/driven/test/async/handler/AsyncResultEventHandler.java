package net.gaox.message.driven.test.async.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.message.driven.async.channel.AsyncChannel;
import net.gaox.message.driven.event.Event;
import net.gaox.message.driven.event.entity.ResultEvent;

import java.util.concurrent.TimeUnit;

/**
 * <p> 结果消息的处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:31
 */
@Slf4j
@AllArgsConstructor
public class AsyncResultEventHandler extends AsyncChannel {
    @Override
    protected void handle(Event message) {
        ResultEvent resultEvent = (ResultEvent) message;
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("get the result is: {}", resultEvent.getResult());
    }

}
