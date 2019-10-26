package net.gaox.designation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.gaox.designation.entity.Cron;
import net.gaox.designation.mapper.CronMapper;
import net.gaox.designation.service.CronService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时表 服务实现类
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-10-26
 */
@Service
@Slf4j
public class CronServiceImpl extends ServiceImpl<CronMapper, Cron> implements CronService {

    @Override
    public String getByName(String name) {
        final Cron one = getOne(new QueryWrapper<Cron>().lambda().select(Cron::getCron)
                .eq(Cron::getName, name).eq(Cron::getAble, 0));
        if (null == one) {
            log.warn("查询[{}]定时器任务，未找到！", name);
            return StringUtils.EMPTY;
        } else {
            log.info("查询[{}]定时器任务，查找到结果[{}]！", name, one.getCron());
            return one.getCron();
        }
    }
}