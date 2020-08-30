package net.gaox.clone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/11/30 13:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HongQi implements Car, Cloneable {
    /**
     * 品牌
     */
    private String brand;
    /**
     * 出厂时间
     */
    private LocalDateTime productionTime;
    /**
     * 价格
     */
    private BigDecimal price;

    @Override
    protected HongQi clone() throws CloneNotSupportedException {
        HongQi obj = (HongQi) super.clone();
        //这里需要对本对象的属性进行深克隆
        obj.price = new BigDecimal(price.toString());
        //这里LocalDateTime是不可变类型，不必担心浅克隆引发的问题
        obj.productionTime = LocalDateTime.of(productionTime.toLocalDate(), productionTime.toLocalTime());
        return obj;
    }

    @Override
    public Boolean allWheel() {
        return true;
    }
}