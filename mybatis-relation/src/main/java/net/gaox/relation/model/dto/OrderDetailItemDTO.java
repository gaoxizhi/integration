package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;

import java.time.LocalDateTime;

/**
 * <p> 订单详情DTO </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class OrderDetailItemDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 商品购买数量
     */
    private Integer itemNum;

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
    /**
     * 商品详情
     */
    private ItemDTO item;

}