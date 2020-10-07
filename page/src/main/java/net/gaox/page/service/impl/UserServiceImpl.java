package net.gaox.page.service.impl;

import net.gaox.page.entity.User;
import net.gaox.page.mapper.UserMapper;
import net.gaox.page.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础用户表 服务实现类
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-09
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> listGaoPage(Integer page, Integer limit) {


        Map<String, Object> data = new HashMap();
        data.put("page", page);
        data.put("size", limit);
        return userMapper.listGaoPage(data);
    }
}