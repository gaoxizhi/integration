package net.gaox.lambda.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.gaox.lambda.entity.enums.EnumDel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
    @NotNull(groups = {Update.class}, message = "修改用户无id信息请检查")
    @Null(groups = Add.class, message = "你怎么会有呢？")
    private Long id;

    @ApiModelProperty(value = "登录名")
    @Length(groups = Default.class, min = 4, max = 16, message = "登录名在4~16位")
    private String userName;

    @ApiModelProperty(value = "真实姓名")
    @NotEmpty(message = "真实姓名不能为空")
    @Length(groups = Default.class, min = 4, max = 16)
    private String realName;

    @ApiModelProperty(value = "密码")
    @Length(groups = Add.class, min = 8, max = 32, message = "密码在6~32位之间！")
    //正则校验强度
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
