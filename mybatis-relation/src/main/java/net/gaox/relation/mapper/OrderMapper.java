package net.gaox.relation.mapper;

import net.gaox.relation.config.mybatis.EnumCodeTypeHandler;
import net.gaox.relation.model.dto.OrderAndOrderDetailsDTO;
import net.gaox.relation.model.dto.UserAndOrderItemDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p> 订单表 mapper 接口 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
public interface OrderMapper {

    /**
     * 查询用户及用户所购买的商品信息
     */
    //查询全部
    @Select("SELECT * FROM `sys_user`")
    @Results({
            // 枚举字段映射处理器
            @Result(column = "sex", property = "sex", typeHandler = EnumCodeTypeHandler.class),
            @Result(
                    property = "orderList",  // 被包含对象的变量名
                    javaType = List.class,  // 被包含对象的实际数据类型
                    column = "id",          // 根据查询出的sys_user表的id字段来查询order表
                    // many、@Many 一对多查询的固定写法
                    // select属性：指定调用哪个接口中的哪个查询方法
                    many = @Many(select = "net.gaox.relation.mapper.OrderMapper.findOrdersByUserId")
            )
    })
    List<UserAndOrderItemDTO> findAllUserOrders();

    @Select("SELECT * FROM `order` WHERE `user_id` = #{userId}")
    @Results({
            @Result(
                    property = "orderDetails", javaType = List.class, column = "id",
                    many = @Many(select = "net.gaox.relation.mapper.OrderDetailMapper.findOrderDetailByOrderId")),
            @Result(column = "del_flag", property = "delFlag", typeHandler = EnumCodeTypeHandler.class)
    })
    List<OrderAndOrderDetailsDTO> findOrdersByUserId(@Param("userId") Long userId);

}