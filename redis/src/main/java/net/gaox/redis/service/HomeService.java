package net.gaox.redis.service;

import net.gaox.redis.entity.Home;

import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName HomeService
 * @Author: gaox·Eric
 * @Date: 2019/4/13 14:41
 */
public interface HomeService {

    Home getHomeByid(Long id);

    Home updata(Home home);

    Home delete(Long id);

    List<Home> homeList();
}
