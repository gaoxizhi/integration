package net.gaox.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.gaox.model.entity.SysUser;
import net.gaox.model.mapper.SysUserMapper;
import net.gaox.model.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}