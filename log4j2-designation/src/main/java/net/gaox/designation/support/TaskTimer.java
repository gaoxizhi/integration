package net.gaox.designation.support;

import lombok.extern.slf4j.Slf4j;
import net.gaox.designation.service.CronService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 * <p> 定时任务管理器 </p>
 * EnableScheduling: 开启定时任务
 *
 * @author gaox·Eric
 * @date 2019/10/26 21:50
 */
//@Component
@Configuration
@EnableScheduling
@Slf4j
public class TaskTimer implements SchedulingConfigurer {
    private CronService cronService;

    public TaskTimer(CronService cronService) {
        this.cronService = cronService;
    }

    /**
     * 执行定时任务
     *
     * @param taskRegistrar 任务注册器
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> log.info("执行动态定时任务: c_5s。"),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronService.getByName("c_5s");
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                        throw new RuntimeException("定时器获取失败！");
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}