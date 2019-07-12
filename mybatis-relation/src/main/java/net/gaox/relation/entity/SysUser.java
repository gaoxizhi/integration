package net.gaox.relation.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Data
public class SysUser {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
//    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
    private Boolean sex;

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
     * 删除标志：删除0；正常1（默认）
     */
    private Boolean delFlag;

    /**
     * 用户创建的订单列表
     */
    private List<Orders> ordersList;

}
