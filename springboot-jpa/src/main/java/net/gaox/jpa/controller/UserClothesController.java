package net.gaox.jpa.controller;

import net.gaox.jpa.entity.User;
import net.gaox.jpa.entity.UserClothes;
import net.gaox.jpa.service.UserClothesService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName UserClothesController
 * @Author: gaox·Eric
 * @Date: 2019/5/2 23:47
 */
@RestController
public class UserClothesController {

    @Autowired
    private UserClothesService userClothesService;
    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @GetMapping("/user/clothes/{id}")
    public List<User> findUsersByClothesId(@PathVariable(value = "id") Long id) {
        logger.debug("查询衣服id： " + id);
        return userClothesService.findUsersByClothes(id);
    }

    @GetMapping("/userClothes")
    public UserClothes getone(){
        logger.debug("查询衣服人员绑定!");
        return userClothesService.findByUserIdAndClothesId(1L,1L);
    }
}
