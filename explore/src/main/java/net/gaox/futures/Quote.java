package net.gaox.futures;

import lombok.Getter;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2022/8/7 03:29
 */
@Getter
public class Quote {
    private String shopName;
    private Double price;
    private DiscountEnum discountEnum;

    public Quote(String shopName, double price, DiscountEnum discountEnum) {
        this.shopName = shopName;
        this.price = price;
        this.discountEnum = discountEnum;
    }

    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        DiscountEnum discountEnum = DiscountEnum.valueOf(split[2]);
        return new Quote(shopName, price, discountEnum);
    }

}