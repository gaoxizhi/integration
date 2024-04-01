package net.gaox.model.controller;


import lombok.RequiredArgsConstructor;
import net.gaox.model.entity.Orders;
import net.gaox.model.service.OrdersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    public String postOrderTest() {
        Orders order = new Orders();
        UUID uuid = UUID.randomUUID();
        String orderNumber = uuid.toString().replaceAll("-", "").substring(0, 16);
        order.setUserId(1L);
        order.setNumber(orderNumber);
        order.setNote("测试订单");
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        ordersService.createOrder(order);

        return "postOrder";
    }

}