package net.gaox.relation.mapper;

import net.gaox.relation.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 mapper 接口
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
public interface SysUserMapper {

    /**
     * 获取列表
     *
     * @return list
     */
    List<SysUser> list();

    /**
     * 插入
     *
     * @param map entry
     * @return num
     */
    int insert(@Param("map") SysUser map);

    /**
     * 通过id查询
     *
     * @param map id
     * @return one
     */
    SysUser selectById(@Param("map") SysUser map);

    /**
     * 标记删除
     *
     * @param map id
     * @return num
     */
    Integer deleteMark(@Param("map") SysUser map);

    /**
     * 删除
     *
     * @param map id
     * @return num
     */
    Integer delete(@Param("map") SysUser map);
}