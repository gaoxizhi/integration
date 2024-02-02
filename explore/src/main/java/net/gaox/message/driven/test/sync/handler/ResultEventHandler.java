package net.gaox.message.driven.test.sync.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.message.driven.event.entity.ResultEvent;
import net.gaox.message.driven.channel.Channel;

/**
 * <p> 结果消息的处理器 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 17:31
 */
@Slf4j
@AllArgsConstructor
public class ResultEventHandler implements Channel<ResultEvent> {
    @Override
    public void dispatch(ResultEvent message) {
        log.info("get the result is: {}", message.getResult());
    }

}
