package net.gaox.jpa.controller;

import lombok.extern.log4j.Log4j2;
import net.gaox.jpa.entity.User;
import net.gaox.jpa.entity.UserClothes;
import net.gaox.jpa.service.UserClothesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gaox·Eric
 * @date 2019/5/2 23:47
 */
@Log4j2
@RestController
public class UserClothesController {

    private final UserClothesService userClothesService;

    public UserClothesController(UserClothesService userClothesService) {
        this.userClothesService = userClothesService;
    }

    @GetMapping("/user/clothes/{id}")
    public List<User> findUsersByClothesId(@PathVariable(value = "id") Long id) {
        log.debug("查询衣服id： " + id);
        return userClothesService.findUsersByClothes(id);
    }

    @GetMapping("/userClothes")
    public UserClothes getone() {
        log.debug("查询衣服人员绑定!");
        return userClothesService.findByUserIdAndClothesId(1L, 1L);
    }
}
