package net.gaox.page.controller;


import net.gaox.page.entity.User;
import net.gaox.page.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> list(@RequestParam(required = false, defaultValue = "1") Integer page,
                           @RequestParam(required = false, defaultValue = "10") Integer limit) {
        List<User> list = userService.listGaoPage(page, limit);
        list.stream().forEach(s -> System.out.println(s.toString()));
        return list;
    }
}