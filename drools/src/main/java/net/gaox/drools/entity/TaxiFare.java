package net.gaox.drools.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p> 出租车价格 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/3 17:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxiFare {
    /**
     * 起步价
     */
    private BigDecimal startFare;
    /**
     * 夜间费用
     */
    private BigDecimal nightSurcharge;
    /**
     * 续租价
     */
    private BigDecimal reletFare;

    public BigDecimal total() {
        return this.startFare.add(this.reletFare).add(nightSurcharge);
    }
}