package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.entity.OrderDetail;
import net.gaox.relation.entity.SysUser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName: OrdersUserDTO
 * @Author: gaox·Eric
 * @Date: 2019/7/12 23:48
 */
@Data
public class OrdersUserDTO {

    /**
     * 添加用户的属性
     */
    private String userName;
    private Boolean sex;
    private String address;
    /**
     * 订单明细
     */
    private List<OrderDetail> orderDetails;
}
