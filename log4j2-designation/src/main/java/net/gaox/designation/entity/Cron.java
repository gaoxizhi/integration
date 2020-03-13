package net.gaox.designation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 定时表
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Cron对象", description = "定时表")
public class Cron extends Model<Cron> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "cron表达式")
    @TableField("cron")
    private String cron;

    @ApiModelProperty(value = "cron名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "cron描述")
    @TableField("cron_desc")
    private String cronDesc;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "状态：默认0可用，1禁用，2删除")
    @TableField("able")
    private Integer able;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}