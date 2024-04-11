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
import java.time.LocalDateTime;

/**
 * <p> 设备总表 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Equipment对象", description = "设备表")
public class Equipment extends Model<Equipment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Mac地址")
    @TableField("mac")
    private String mac;

    @TableField("home_id")
    private Long homeId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "上次修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "禁用/启用 (0,1)")
    @TableField("able")
    private Integer able;

    @ApiModelProperty(value = "类型")
    @TableField("type_id")
    private Integer typeId;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
