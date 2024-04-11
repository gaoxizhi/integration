package net.gaox.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p> 用户家庭关联表 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RUserHome对象", description = "用户家庭关联表")
public class RUserHome extends Model<RUserHome> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联家庭id")
    @TableField("home_id")
    private Long homeId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
