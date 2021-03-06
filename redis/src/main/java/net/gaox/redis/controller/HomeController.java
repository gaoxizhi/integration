package net.gaox.redis.controller;

import net.gaox.redis.entity.Home;
import net.gaox.redis.service.HomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gaox·Eric
 * @date 2019/4/13 14:35
 */
@RestController
public class HomeController {
    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/home/{id}")
    public Home getHome(@PathVariable String id) {
        System.out.println("接受查询请求，ID：" + id);
        return homeService.getHomeByid(Long.parseLong(id));
    }

    @PatchMapping("/home")
    public Home update(@RequestBody Home home) {
        System.out.println("调用修改：" + home.getAlias());
        return homeService.updata(home);
    }

    @DeleteMapping("/home/{id}")
    public Home delete(@PathVariable String id) {
        System.out.println("调用删除id：" + id);
        return homeService.delete(Long.parseLong(id));
    }

    @GetMapping("/home/list")
    public List<Home> getList() {
        return homeService.homeList();
    }
}