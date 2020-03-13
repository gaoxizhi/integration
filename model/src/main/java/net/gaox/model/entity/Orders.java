package net.gaox.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Orders对象", description = "订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "下单用户id")
    private Long userId;

    @ApiModelProperty(value = "订单号")
    private String number;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "上次修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志：删除0；正常1（默认）")
    private Boolean delFlag;
}