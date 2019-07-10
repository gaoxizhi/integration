package net.gaox.relation.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class Orders {

    private static final long serialVersionUID = 1L;

    //    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 下单用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private String number;

    /**
     * 备注
     */
    private String note;

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
    private Boolean delFlag;

    /**
     * 用户信息
     */
    private SysUser user;
    /**
     * 订单明细
     */
    private List<OrderDetail> orderDetails;
}
