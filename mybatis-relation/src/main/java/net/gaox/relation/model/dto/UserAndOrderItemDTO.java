package net.gaox.relation.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.gaox.relation.model.enums.EnumDelFlag;
import net.gaox.relation.model.enums.EnumSex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p> 用户表 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class UserAndOrderItemDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 性别
     */
    private EnumSex sex;

    /**
     * 地址
     */
    private String address;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 上次修改时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 账户状态: 0 删除, 1 正常, 2 注销
     */
    private EnumDelFlag state;

    /**
     * 用户创建的订单列表
     */
    private List<OrderAndOrderDetailsDTO> orderList;

}
