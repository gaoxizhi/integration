package net.gaox.jpa.controller;

import net.gaox.jpa.entity.User;
import net.gaox.jpa.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Description: <p>  </p>
 * @ClassName UserController
 * @author gaox·Eric
 * @date 2019/5/2 15:34
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        logger.debug("查询用户" + id);
        return userService.getOne(id);
    }

    @GetMapping("/user/get1")
    public User getUser1() {
        Optional<User> userDOOptional = userService.findById(1L);
        User user = userDOOptional.get();
        if (userDOOptional.isPresent()) {
            System.out.println("name = " + user.getName());
            System.out.println("account = " + user.getSpCode());
        }
        return user;
    }
}
