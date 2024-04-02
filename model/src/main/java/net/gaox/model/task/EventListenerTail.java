package net.gaox.model.task;

import lombok.extern.slf4j.Slf4j;
import net.gaox.model.entity.Orders;
import net.gaox.model.enums.EnumUtils;
import net.gaox.model.enums.OrderSendStatusEnum;
import net.gaox.model.events.OrderSendEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p> 事件集中监听 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 12:20
 */
@Slf4j
@Component
public class EventListenerTail {

    /**
     * 监听事件，可设置多个，配置优先级，配置异步
     *
     * @param event 事件
     */
    @Async
    @Order(-1)
    @EventListener(OrderSendEvent.class)
    public void eventListener(OrderSendEvent event) {
        event = Optional.ofNullable(event).orElse(new OrderSendEvent());
        Orders order = event.getOrder();
        OrderSendStatusEnum eventType = event.getEventType();
        Object enumStr = EnumUtils.isValueOf(eventType, OrderSendStatusEnum.class) ? eventType.getDesc() : eventType;
        log.debug("[OrderSendEvent事件], 类型[{}], 内容：{}", enumStr, order);
    }

}
