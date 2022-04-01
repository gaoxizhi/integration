package net.gaox.relation.controller;

import lombok.extern.log4j.Log4j2;
import net.gaox.relation.entity.Orders;
import net.gaox.relation.entity.SysUser;
import net.gaox.relation.mapper.OrdersCustomMapper;
import net.gaox.relation.mapper.SysUserMapper;
import net.gaox.relation.model.dto.OrderDetailCustomDTO;
import net.gaox.relation.model.dto.OrdersCustomTypeDTO;
import net.gaox.relation.model.enums.EnumDelFlag;
import net.gaox.relation.model.enums.EnumSex;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
@Log4j2
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private OrdersCustomMapper ocMapper;

    @GetMapping
    public SysUser get(Long id) {
        SysUser user = new SysUser();
        user.setId(id);
        return userMapper.selectById(user);
    }

    @GetMapping("/list")
    public List<SysUser> list() {
        return userMapper.list();
    }

    @GetMapping("/findOrdersUser")
    public List<OrdersCustomTypeDTO> findOrdersUser() {
        return ocMapper.findOrdersUser();
    }

    @GetMapping("/findOrdersUserResultMap")
    public List<Orders> findOrdersUserResultMap() {
        return ocMapper.findOrdersUserResultMap();
    }

    @GetMapping("/findOrdersAndOrderDetailResultMap")
    public List<OrderDetailCustomDTO> findOrdersAndOrderDetailResultMap() {
        return ocMapper.findOrdersAndOrderDetailResultMap();
    }

    @PostMapping
    public Object insert() {
        SysUser user = new SysUser();
        user.setUserName("高羲之");
        user.setSex(EnumSex.MALE);
        user.setBirthday(LocalDate.now());
        user.setAddress("山东省曹县梁堤头镇杨集村");
        int insert = userMapper.insert(user);
        log.info("插入数量{}个，成功{}个。", 1, insert);
        //这里取值除了设置的，多的只有id
        log.debug(user.toString());
        user = userMapper.selectById(user);
        log.debug(user.toString());
        return user;
    }

    @DeleteMapping
    public SysUser delete(Long id) {
        SysUser user = new SysUser();
        user.setId(id);
        SysUser sysUser = userMapper.selectById(user);
        sysUser.setDelFlag(EnumDelFlag.DELETE);
        userMapper.deleteMark(sysUser);
        return sysUser;
    }

    @GetMapping("/findUserAndItemsResultMap")
    public List<SysUser> findUserAndItemsResultMap() {
        return ocMapper.findUserAndItemsResultMap();
    }
}