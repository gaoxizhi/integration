package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.entity.Items;
import net.gaox.relation.model.enums.EnumDelFlag;

import java.time.LocalDateTime;

/**
 * @Description: <p>  </p>
 * @ClassName: OrderDetailCustomDTO
 * @Author: gaox·Eric
 * @Date: 2019/7/14 20:30
 */
@Data
public class OrderDetailCustomDTO {
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
