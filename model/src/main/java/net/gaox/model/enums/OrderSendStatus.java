package net.gaox.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * <p> 消息投递状态 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 13:00
 */
@Getter
@AllArgsConstructor
public enum OrderSendStatus {

    ORDER_SENDING("0", "消息投递中"),
    ORDER_SEND_SUCCESS("1", "消息投递成功"),
    ORDER_SEND_FAILURE("2", "消息投递失败");

    private String code;
    private String desc;

    /**
     * 校验code
     *
     * @param code code
     * @return 所属
     */
    public boolean checkCode(String code) {
        if (null == code) {
            return false;
        }
        String trimCode = StringUtils.trimAllWhitespace(code);
        boolean match = Arrays.stream(OrderSendStatus.values())
                .anyMatch(s -> s.getCode().equalsIgnoreCase(trimCode));
        return match;
    }

}
