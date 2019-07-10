package net.gaox.relation.controller;

import net.gaox.relation.entity.OrderDetail;
import net.gaox.relation.entity.Orders;
import net.gaox.relation.entity.SysUser;
import net.gaox.relation.mapper.OrdersCustomMapper;
import net.gaox.relation.mapper.SysUserMapper;
import net.gaox.relation.model.dto.OrdersCustomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private OrdersCustomMapper ocMapper;

    @GetMapping("/list")
    public List<SysUser> list() {
        return userMapper.list();
    }

    @GetMapping("")
    public String string() {
        return "sddddddddddddddddddddddddddddddddddddddd";
    }

    @GetMapping("/findOrdersUser")
    public List<OrdersCustomDTO> findOrdersUser() {
        return ocMapper.findOrdersUser();
    }

    @GetMapping("/findOrdersUserResultMap")
    public List<Orders> findOrdersUserResultMap() {
        return ocMapper.findOrdersUserResultMap();
    }

    @GetMapping("/findOrdersAndOrderDetailResultMap")
    public List<OrderDetail> findOrdersAndOrderDetailResultMap() {
        return ocMapper.findOrdersAndOrderDetailResultMap();
    }

    @GetMapping("/findUserAndItemsResultMap")
    public List<SysUser> findUserAndItemsResultMap() {
        return ocMapper.findUserAndItemsResultMap();
    }

}

