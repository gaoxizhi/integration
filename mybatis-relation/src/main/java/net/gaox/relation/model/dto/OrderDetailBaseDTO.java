package net.gaox.relation.model.dto;

import lombok.Data;

/**
 * <p> 订单基础信息DTO </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class OrderDetailBaseDTO {
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

}
