package net.gaox.jpa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.gaox.jpa.base.BaseEnum;
import net.gaox.jpa.util.BaseEnumUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * <p> 状态枚举 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 22:22
 */

@Getter
@AllArgsConstructor
public enum StateEnum implements BaseEnum<Integer> {

    DELETE(0, "删除"),
    NORMAL(1, "正常"),
    DISABLE(2, "禁用"),
    CANCEL(3, "注销"),
    ;

    private final Integer code;
    private final String name;

    public static StateEnum getByCode(Integer code) {
        return BaseEnumUtils.find(code, StateEnum.class);
    }


    public static StateEnum getByName(Integer code) {

        Optional<StateEnum> first = Arrays.stream(StateEnum.values()).filter(s -> s.getCode().equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }


}
