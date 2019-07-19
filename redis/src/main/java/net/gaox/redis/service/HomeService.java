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
    /**
     * 通过id查询家庭
     *
     * @param id
     * @return
     */
    Home getHomeByid(Long id);

    /**
     * 更新家庭信息
     *
     * @param home
     * @return
     */
    Home updata(Home home);

    /**
     * 删除家庭信息
     *
     * @param id
     * @return
     */
    Home delete(Long id);

    /**
     * 获取家庭列表
     *
     * @return
     */
    List<Home> homeList();
}
