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
 * <p> 程序初始化表，加载家庭中设备 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RHomeEq对象", description = "程序初始化表，加载家庭中设备")
public class RHomeEq extends Model<RHomeEq> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "绑定家庭编号")
    @TableField("home_id")
    private Long homeId;

    @ApiModelProperty(value = "类型id")
    @TableField("type_id")
    private Integer typeId;

    @ApiModelProperty(value = "在设备表id")
    @TableField("eq_id")
    private Long eqId;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
