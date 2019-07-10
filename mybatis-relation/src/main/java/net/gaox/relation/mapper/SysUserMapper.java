package net.gaox.relation.mapper;

import net.gaox.relation.entity.SysUser;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
public interface SysUserMapper {

    List<SysUser> list();
}
