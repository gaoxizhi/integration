package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.entity.OrderDetail;
import net.gaox.relation.model.enums.EnumSex;

import java.util.List;

/**
 * @author gaox·Eric
 * @date 2019/7/12 23:48
 */
@Data
public class OrdersUserDTO {

    /**
     * 添加用户的属性
     */
    private String userName;
    private EnumSex sex;
    private String address;
    /**
     * 订单明细
     */
    private List<OrderDetail> orderDetails;
}