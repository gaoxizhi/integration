package net.gaox.relation.mapper;

import net.gaox.relation.entity.Orders;
import net.gaox.relation.entity.SysUser;
import net.gaox.relation.model.dto.OrderDetailCustomDTO;
import net.gaox.relation.model.dto.OrdersCustomTypeDTO;

import java.util.List;

/**
 * per
 *
 * @author gaox·Eric
 * @date 2019/7/10 21:35
 */
public interface OrdersCustomMapper {

    /**
     * 查询订单，关联查询用户信息
     */
    List<OrdersCustomTypeDTO> findOrdersUser();

    /**
     * 查询订单关联查询用户信息，使用reslutMap实现
     */
    List<Orders> findOrdersUserResultMap();

    /**
     * 查询订单（关联用户）以及订单明细
     */
    List<OrderDetailCustomDTO> findOrdersAndOrderDetailResultMap();

    /**
     * 查询用户及用户所购买的商品信息
     */
    List<SysUser> findUserAndItemsResultMap();
}