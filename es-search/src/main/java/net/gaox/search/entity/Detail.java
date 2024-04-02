package net.gaox.search.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p> 商品详情 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 22:28
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Detail {

    /**
     * 品牌
     */
    private String brand;

    /**
     * 成分
     */
    private String ingredient;

    /**
     * 上市时间
     */
    private LocalDate releaseTime;

    /**
     * 净含量
     */
    private BigDecimal netContent;

}
