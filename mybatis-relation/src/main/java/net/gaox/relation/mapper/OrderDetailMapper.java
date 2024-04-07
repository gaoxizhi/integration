package net.gaox.relation.mapper;

import net.gaox.relation.config.mybatis.EnumCodeTypeHandler;
import net.gaox.relation.entity.Item;
import net.gaox.relation.model.dto.OrderDetailItemDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p> 订单详情 mapper 接口 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
public interface OrderDetailMapper {

    @Select("SELECT * FROM `order_detail` WHERE `order_id` = #{orderId}")
    @Results({
            @Result(
                    property = "item", javaType = Item.class, column = "item_id",
                    // one、@One 一对一固定写法
                    one = @One(select = "net.gaox.relation.mapper.ItemMapper.findItemById")),
            @Result(column = "del_flag", property = "delFlag", typeHandler = EnumCodeTypeHandler.class)
    })
    List<OrderDetailItemDTO> findOrderDetailByOrderId(@Param("orderId") Long orderId);

}