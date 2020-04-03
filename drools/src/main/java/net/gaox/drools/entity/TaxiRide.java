package net.gaox.drools.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p> 出租车乘坐信息对象 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/3 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxiRide {
    private Boolean isNightSurcharge;
    private BigDecimal distanceInMile;
}