package net.gaox.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.gaox.enums.EnumDel;

import java.time.LocalDateTime;

/**
 * <p> 用户表 </p>
 *
 * @author gaox
 * @since 2019-07-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "密码")
    private String passWord;

    @ApiModelProperty(value = "随机盐")
    private String salt;

    @ApiModelProperty(value = "简介")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "上次修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "禁用/启用 （0,1）")
    private EnumDel able;
    /**
     * 删除，正常，禁用，限制操作，暂未启用，
     */
    @ApiModelProperty(value = "删除/正常 （0,1）")
    private EnumDel delFlag;

    public interface Default {
    }

    public interface Add extends Default {
    }

    public interface Update extends Default {
    }
}
