package net.gaox.search.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p> 产品 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 17:00
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String category;
    private BigDecimal price;
    private String place;
    private String code;
}
