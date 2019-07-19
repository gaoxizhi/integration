package net.gaox.relation.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: <p>  </p>
 * @ClassName: OrderTypeDTO
 * @Author: gaox·Eric
 * @Date: 2019/7/14 20:26
 */
@Data
public class OrderTypeDTO {
    private Long id;

    /**
     * 下单用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private String number;

    /**
     * 备注
     */
    private String note;

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
