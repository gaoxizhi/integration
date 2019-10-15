package net.gaox.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.gaox.model.model.enums.EnumDel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@SuppressWarnings("serial")
@ApiModel(value = "Items对象", description = "商品表")
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String itemsName;

    @ApiModelProperty(value = "商品定价")
    private BigDecimal price;

    @ApiModelProperty(value = "商品描述")
    private String detail;

    @ApiModelProperty(value = "商品图片")
    private String pic;

    @ApiModelProperty(value = "生产日期")
    private LocalDate produceTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "上次修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志：删除0；正常1（默认）")
    private EnumDel delFlag;
}