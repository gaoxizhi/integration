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
 * <p> SysKv </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysKv对象", description = "")
public class SysKv extends Model<SysKv> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "键")
    @TableField("k")
    private String k;

    @ApiModelProperty(value = "值")
    @TableField("v")
    private String v;

    @ApiModelProperty(value = "创建者")
    @TableField("creator")
    private Long creator;

    @ApiModelProperty(value = "修改者")
    @TableField("modifier")
    private Long modifier;

    @ApiModelProperty(value = "创建时间")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "上次修改时间")
    @TableField("gmt_modifyed")
    private LocalDateTime gmtModifyed;

    @TableField("state")
    private Integer state;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
