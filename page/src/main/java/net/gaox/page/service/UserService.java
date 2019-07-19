package net.gaox.page.service;

import net.gaox.page.entity.User;

import java.util.List;

/**
 * <p>
 * 基础用户表 服务类
 * </p>
 *
 * @author GengYun
 * @since 2019-07-09
 */
public interface UserService {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    List<User> listGaoPage(Integer page, Integer limit);
}
