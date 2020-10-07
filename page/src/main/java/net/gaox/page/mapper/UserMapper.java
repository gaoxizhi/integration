package net.gaox.page.mapper;

import net.gaox.page.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础用户表 mapper 接口
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-09
 */
public interface UserMapper {

    /**
     * 查询列表
     *
     * @param data 查询条件
     * @return
     */
    List<User> listGaoPage(@Param("map") Map<String, Object> data);

    /**
     * 插入用户
     *
     * @return 执行条数
     */
    Integer insert();
}