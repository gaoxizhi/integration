package net.gaox.relation.entity;

import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class OrderDetail {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
//         @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private Long ordersId;

    /**
     * 商品id
     */
    private Long itemsId;

    /**
     * 商品购买数量
     */
    private Integer itemsNum;

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
    private Items item;

}
