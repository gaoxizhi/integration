package net.gaox.message.handle.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * <p> 监听异常处理 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-10 14:23
 */
@Slf4j
@Component
public class ListenerErrorHandler implements KafkaListenerErrorHandler {

    @Override
    @NonNull
    public Object handleError(@NonNull Message<?> message, @NonNull ListenerExecutionFailedException exception) {
        return new Object();
    }

    @Override
    @NonNull
    public Object handleError(@NonNull Message<?> message, @NonNull ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        log.warn("监听到异常: 消息详情 = {}, \n异常信息", message, exception);
        return KafkaListenerErrorHandler.super.handleError(message, exception, consumer);
    }

}
