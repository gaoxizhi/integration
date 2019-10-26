package net.gaox.designation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.gaox.designation.entity.Cron;

/**
 * <p>
 * 定时表 服务类
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-10-26
 */
public interface CronService extends IService<Cron> {
    /**
     * 通过名称获取定时配置
     *
     * @param name 名称
     * @return 定时配置
     */
    String getByName(String name);

}