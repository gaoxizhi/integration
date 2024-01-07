package net.gaox.futures;

import java.util.Random;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2022/8/7 04:27
 */
public class ShopService {

    /**
     * 获取价格
     */
    public static Double getPrice(String product) {
        // 查询商品的数据库，或链接其他外部服务获取折扣
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static String getProductInfo(Product product) {

        DiscountEnum discountEnum = DiscountEnum.values()[new Random().nextInt(DiscountEnum.values().length)];
        return String.format("%s:%.2f:%s", product.getName(), getPrice(product.getName()), discountEnum);
    }


    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + "price :" + apply(quote.getPrice(), quote.getDiscountEnum());
    }

    public static double apply(double price, DiscountEnum discountEnum) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return price * (100 - discountEnum.getValue()) / 100;
    }

}
