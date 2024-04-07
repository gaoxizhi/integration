package net.gaox.relation.entity;

import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p> 订单表 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
    private EnumDelFlag delFlag;

}