package net.gaox.designation.model.dos;

import lombok.Data;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/26 22:05
 */
@Data
public class CronDO {

    private Integer id;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * cron名称
     */
    private String name;
    /**
     * 定时描述
     */
    private String cronDesc;
    /**
     * 可用性
     */
    private Integer able;
}