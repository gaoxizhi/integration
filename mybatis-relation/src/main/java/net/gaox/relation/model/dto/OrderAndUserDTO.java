package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;

import java.time.LocalDateTime;

/**
 * <p> 订单的扩展类,通过此类映射订单和用户的查询结果,让此类继承字段较多的实体类 </p>
 *
 * @author gaox·Eric
 * @date 2019/7/10 21:31
 */
@Data
public class OrderAndUserDTO {

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

    /**
     * 用户信息
     */
    private UserBaseDTO user;

}