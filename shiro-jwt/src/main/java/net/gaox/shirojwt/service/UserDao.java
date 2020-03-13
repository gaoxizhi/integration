package net.gaox.shirojwt.service;

import net.gaox.shirojwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 UserDao
 * @author gaox·Eric
 * @date 2019/5/4 00:58
 */
//@Service
public class UserDao {

    @Autowired
    private UserService userService;


    public User getUser(String username) {
        final User user = userService.getUserByName(username);
        // 没有此用户直接返回null
        return null == user ? null : user;
    }
}