package net.gaox.model.controller;


import net.gaox.model.entity.SysUser;
import net.gaox.model.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gaoxÂ·Eric
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @RequestMapping("")
    public List<SysUser> list() {
        List<SysUser> list = userService.list(null);
        list.stream().forEach(s -> System.out.println(s.toString()));
        return list;
    }

}
