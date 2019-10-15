package net.gaox.shirojwt.controller;

import net.gaox.shirojwt.entity.User;
import net.gaox.shirojwt.service.UserService;
import net.gaox.shirojwt.util.JWTUtil;
import net.gaox.shirojwt.util.api.ApiResponse;
import net.gaox.shirojwt.util.exception.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 UserController
 * @author gaox·Eric
 * @date 2019/5/2 15:34
 */
@RestController
public class UserController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String hello() {

        return "Hello from <a href = \"http://gaox.net!\">gaox.net</a>";
    }
    @PostMapping("/user/login")
    public ApiResponse login(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        User user = userService.getUserByName(username);
        if (null == user) {
            throw new UnauthorizedException();
        }
        if (user.getPassword().equals(password)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", 200);
            map.put("info", "Login success");
            map.put("gaox-to", JWTUtil.sign(username, password));
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
    @GetMapping("/user/{id}")
    @RequiresAuthentication
    public User getUser(@PathVariable Long id) {
        logger.debug("查询用户" + id);
        return userService.getOne(id);
    }
    @GetMapping("/user/get1")
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
    @GetMapping("/user/all")
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
    @GetMapping("/user/info")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ApiResponse getInfo(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader("gaox-to");
        final String username = JWTUtil.getUsername(jwt);
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
    @PostMapping("/user")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ApiResponse addUser(@RequestBody User user) {
        logger.debug("添加一个用户：" + user);
        final User getUser = userService.save(user);
        return ApiResponse.success().and("data", getUser);
    }
}