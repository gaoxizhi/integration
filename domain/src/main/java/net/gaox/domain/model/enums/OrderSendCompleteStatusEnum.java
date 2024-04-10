package net.gaox.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 消息处理状态 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 13:00
 */
@Getter
@AllArgsConstructor
public enum OrderSendCompleteStatusEnum {
    SUCCESS("1", "订单处理成功"),
    FAILURE("2", "订单处理失败"),
    ;

    private String code;
    private String desc;

}
