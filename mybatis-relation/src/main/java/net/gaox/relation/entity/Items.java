package net.gaox.relation.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;

import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class Items {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
//         @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String itemsName;

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
