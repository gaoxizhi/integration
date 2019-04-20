package net.gaox.mongodb.controller;

import com.alibaba.fastjson.JSONObject;
import net.gaox.mongodb.pojo.User;
import net.gaox.mongodb.service.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 * @Date: 2019/1/22 08:54
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServer userServer;

    /**
     * 通过用户名查询用户
     *
     * @param name
     * @return
     */
    @GetMapping("/{name}")
    public User find(@PathVariable("name") String name) {
        System.out.println(name);
        return userServer.findByName(name);
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @PostMapping("")
    public User save(@RequestBody User user) {
        userServer.saveUser(user);
        return user;
    }

    /**
     * 更新对象
     *
     * @param user
     * @return
     */
    @PatchMapping("")
    public User upDate(@RequestBody User user) {
        System.out.println(user);
        return userServer.update(user);
    }

    @GetMapping("/list")
    public List<User> findAll() {
        System.out.println("=============查询所有==============");
        return userServer.findAll();
    }

    /**
     * 通过id删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public JSONObject delete(@PathVariable("id") String id) {
        JSONObject jsonObject = new JSONObject();
        Boolean deleted = userServer.deleteById(id);
        if (null == deleted) {
            jsonObject.put("err", "删除失败，没找到这个值");
        } else if (deleted) {
            jsonObject.put("suc", "删除成功");

        } else {
            jsonObject.put("err", "删除失败");
        }
        return jsonObject;
    }
}
