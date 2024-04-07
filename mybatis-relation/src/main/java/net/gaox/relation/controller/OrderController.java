package net.gaox.relation.controller;

import net.gaox.relation.mapper.OrderCustomMapper;
import net.gaox.relation.model.dto.OrderAndOrderDetailsDTO;
import net.gaox.relation.model.dto.OrderAndUserDTO;
import net.gaox.relation.model.dto.OrderUserDTO;
import net.gaox.relation.model.dto.UserAndOrderItemDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 订单表 前端控制器 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderCustomMapper orderCustomMapper;

    @GetMapping("/findOrderUser")
    public List<OrderUserDTO> findOrderUser() {
        return orderCustomMapper.findOrderUser();
    }

    @GetMapping("/findOrderUserResultMap")
    public List<OrderAndUserDTO> findOrderUserResultMap() {
        return orderCustomMapper.findOrderUserResultMap();
    }

    @GetMapping("/findOrderAndOrderDetailResultMap")
    public List<OrderAndOrderDetailsDTO> findOrderAndOrderDetailResultMap() {
        return orderCustomMapper.findOrderAndOrderDetailResultMap();
    }

    @GetMapping("/findUserAndItemResultMap")
    public List<UserAndOrderItemDTO> findUserAndItemResultMap() {
        return orderCustomMapper.findUserAndItemResultMap();
    }

}