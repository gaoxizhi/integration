package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;
import net.gaox.relation.model.enums.EnumSex;

import java.time.LocalDateTime;

/**
 * <p> 订单的扩展类,通过此类映射订单和用户的查询结果,让此类继承字段较多的实体类 </p>
 *
 * @author gaox·Eric
 * @date 2019/7/14 20:26
 */
@Data
public class OrderUserDTO {

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
     * 添加用户的属性
     */
    private String userName;
    private EnumSex sex;
    private String address;

}