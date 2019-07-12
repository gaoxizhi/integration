package net.gaox.relation.controller;

import net.gaox.relation.entity.OrderDetail;
import net.gaox.relation.entity.Orders;
import net.gaox.relation.entity.SysUser;
import net.gaox.relation.mapper.OrdersCustomMapper;
import net.gaox.relation.mapper.SysUserMapper;
import net.gaox.relation.model.dto.OrdersCustomDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private OrdersCustomMapper ocMapper;

    @GetMapping("/list")
    public List<SysUser> list() {
        return userMapper.list();
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

    @PostMapping("")
    public Object insert() {
        SysUser user = new SysUser();
        user.setUserName("高羲之");
        user.setSex(true);
        user.setBirthday(LocalDate.now());
        user.setAddress("山东省曹县梁堤头镇杨集村");
        int insert = userMapper.insert(user);
        logger.info("插入数量{}个，成功{}个。", insert, insert);
        //这里取值除了设置的，多的只有id
        logger.debug(user.toString());
        return user;
    }

    @GetMapping("/findUserAndItemsResultMap")
    public List<SysUser> findUserAndItemsResultMap() {
        return ocMapper.findUserAndItemsResultMap();
    }

}

