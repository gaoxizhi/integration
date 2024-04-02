package net.gaox.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.gaox.model.entity.Orders;
import net.gaox.model.enums.OrderSendStatusEnum;

/**
 * <p> 订单同步时间消息体 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-03 11:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSendEvent {
    private OrderSendStatusEnum eventType;
    private Orders order;
}
