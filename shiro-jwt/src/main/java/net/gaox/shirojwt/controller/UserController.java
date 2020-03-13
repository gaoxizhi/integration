package net.gaox.shirojwt.controller;

import lombok.extern.slf4j.Slf4j;
import net.gaox.shirojwt.entity.User;
import net.gaox.shirojwt.service.UserService;
import net.gaox.shirojwt.util.jwt.JwtUtil;
import net.gaox.util.api.ApiResponse;
import net.gaox.util.exception.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * UserController
 *
 * @author gaox·Eric
 * @date 2019/5/2 15:34
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ApiResponse login(@RequestBody User user) {
        user = userService.getUserByName(user.getName());
        System.out.println(user);
        if (null == user) {
            throw new UnauthorizedException();
        }
        if (user.getPassword().equals(user.getPassword())) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", 200);
            map.put("info", "Login success");
            map.put("gaox-to", JwtUtil.sign(user.getName(), user.getPassword()));
            return ApiResponse.success().and(map);
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse unauthorized() {
        return ApiResponse.fail().and("code", 401).error("Unauthorized");
    }

    @GetMapping("/{id}")
    @RequiresAuthentication
    public User getUser(@PathVariable Long id) {
        log.debug("查询用户" + id);
        return userService.getOne(id);
    }

    @GetMapping("/get1")
    @RequiresRoles("admin")
    public User getUser1() {
        Optional<User> userDOOptional = userService.findById(1L);
        User user = userDOOptional.get();
        if (userDOOptional.isPresent()) {
            System.out.println("name = " + user.getName());
            System.out.println("account = " + user.getSpCode());
        }
        return user;
    }

    @GetMapping("/all")
    @RequiresRoles("admin")
    public ApiResponse getAllUser() {
        final List<User> all = userService.findAll();
        return ApiResponse.success().data(all);
    }

    /**
     * 通过jwt获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/info")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ApiResponse getInfo(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader("gaox-to");
        final String username = JwtUtil.getUsername(jwt);
        final User user = userService.getUserByName(username);
        return ApiResponse.success().data(user);
    }

    /**
     * 权限鉴定
     * RequiresPermissions logical.and 权限都存在
     * 同时必须登录
     *
     * @param user
     * @return
     */
    @PostMapping
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ApiResponse addUser(@RequestBody User user) {
        log.debug("添加一个用户：" + user);
        final User getUser = userService.save(user);
        return ApiResponse.success().and("data", getUser);
    }
}