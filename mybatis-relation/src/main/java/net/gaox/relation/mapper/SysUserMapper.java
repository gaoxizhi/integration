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

    List<SysUser> list();

    int insert(@Param("map") SysUser map);

    SysUser selectById(@Param("map") SysUser map);
}