package net.gaox.relation.model.dto;

import lombok.Data;
import net.gaox.relation.entity.Orders;
import net.gaox.relation.model.enums.EnumSex;

/**
 * @Description: <p> 订单的扩展类,通过此类映射订单和用户的查询结果,让此类继承字段较多的实体类 </p>
 * @ClassName: OrdersCustomDTO
 * @author gaox·Eric
 * @date 2019/7/10 21:31
 */
@Data
public class OrdersCustomDTO extends Orders {

    /**
     * 添加用户的属性
     */
    private String userName;
    private EnumSex sex;
    private String address;
}
