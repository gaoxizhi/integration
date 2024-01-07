package net.gaox.futures;

import lombok.Getter;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2022/8/7 03:26
 */
@Getter
public enum DiscountEnum {
    NONE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15),
    DIAMOND(20);

    private final int value;

    DiscountEnum(int value) {
        this.value = value;
    }

}
