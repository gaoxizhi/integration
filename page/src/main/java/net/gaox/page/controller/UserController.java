package net.gaox.page.controller;


import net.gaox.page.entity.User;
import net.gaox.page.mapper.UserMapper;
import net.gaox.page.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 基础用户表 前端控制器
 * </p>
 *
 * @author gaox·Eric
 * @since : 2019/7/9
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(@RequestParam Integer page, @RequestParam Integer limit) {
        List<User> list = userService.listGaoPage(page, limit);
//        list.stream().forEach(s -> System.out.println(s.toString()));
        return list;
    }
}