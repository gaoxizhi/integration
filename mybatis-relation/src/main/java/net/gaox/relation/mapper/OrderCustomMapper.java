package net.gaox.relation.mapper;

import net.gaox.relation.model.dto.OrderAndOrderDetailsDTO;
import net.gaox.relation.model.dto.OrderAndUserDTO;
import net.gaox.relation.model.dto.OrderUserDTO;
import net.gaox.relation.model.dto.UserAndOrderItemDTO;

import java.util.List;

/**
 * <p> 订单表聚合查询Mapper </p>
 *
 * @author gaox·Eric
 * @date 2019/7/10 21:35
 */
public interface OrderCustomMapper {

    /**
     * 查询订单，关联查询用户信息
     */
    List<OrderUserDTO> findOrderUser();

    /**
     * 查询订单关联查询用户信息，使用reslutMap实现
     */
    List<OrderAndUserDTO> findOrderUserResultMap();

    /**
     * 查询订单（关联用户）以及订单明细
     */
    List<OrderAndOrderDetailsDTO> findOrderAndOrderDetailResultMap();

    /**
     * 查询用户及用户所购买的商品信息
     */
    List<UserAndOrderItemDTO> findUserAndItemResultMap();

}
