package net.gaox.designation.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 日志分离设置 </p>
 *
 * @author gaox·Eric
 * @date 2019/10/17 00:30
 */
@RestController
public class LogController {

    private Logger log = Logger.getLogger(LogController.class);

    @GetMapping("/class")
    public JSONPObject logging() {
        log.debug("新请求访问：{class}");
        return new JSONPObject("meg", "访问class成功！");
    }

    @GetMapping("/eq")
//    @Scheduled(cron = "*/1 * * * * ?")
    public JSONPObject eqLogging() {
        log.info("新请求访问：{eq}");
        return new JSONPObject("meg", "访问eq成功！");
    }

    /**
     * 17-20点每分钟
     * 在每天下午17:00点到下午19:59期间的每1分钟触发
     */
    @Scheduled(cron = "0 * 0-8 * * ?")
    @Scheduled(cron = "0 * 17-23 * * ?")
    private void trace1() {
        log.error("正分钟播报！");
    }

    /**
     * 20的整数倍秒 和 30秒
     */
    @Scheduled(cron = "*/20 * * * * ?")
    @Scheduled(cron = "30 * * * * ?")
    private void trace() {
        log.warn("定时期任务");
    }

}