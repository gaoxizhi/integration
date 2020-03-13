package net.gaox.page.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 基础用户表
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-09
 */
@Data
public class User {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 性别：女0；男1
     */
    private Boolean gender;

    /**
     * 工资
     */
    private BigDecimal salary;

    /**
     * 地址
     */
    private String address;

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
}