package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p> 商品DTO </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class ItemDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品定价
     */
    private BigDecimal price;

    /**
     * 商品描述
     */
    private String detail;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 生产日期
     */
    private LocalDate produceTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 上次修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标志：删除0；正常1（默认）
     */
    private EnumDelFlag delFlag;

}
