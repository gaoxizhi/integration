package net.gaox.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 消息投递状态 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 13:00
 */
@Getter
@AllArgsConstructor
public enum OrderSendStatusEnum {

    ORDER_SENDING("0", "消息投递中"),
    ORDER_SEND_SUCCESS("1", "消息投递成功"),
    ORDER_SEND_FAILURE("2", "消息投递失败");

    private String code;
    private String desc;

}
