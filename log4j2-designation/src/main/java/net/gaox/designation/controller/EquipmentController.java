package net.gaox.designation.controller;


import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 设备总表 前端控制器
 * </p>
 *
 * @author gaox
 * @since 2019-04-07
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "25 * * * * ?")
    private void trace() {
        log.warn("消息推送中！");
    }

    /**
     * https://blog.csdn.net/u013323758/article/details/81126147
     * x/y 数值的增量
     * 从第x开始，每y单位执行
     * ? 字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值
     * <p>
     * fixedDelay 上一次执行完毕时间点之后多长时间再执行 （秒）
     * fixedRate 上一次开始执行时间点之后多长时间再执行 （秒）
     * initialDelay 第一次延迟多长时间后再执行 （秒）
     * <p>
     * zone 时区，接收一个java.util.TimeZone#ID。cron表达式会基于该时区解析。
     * 默认是一个空字符串，即取服务器所在地的时区。比如我们一般使用的时区Asia/Shanghai。
     */
    @Scheduled(fixedRate = 90_000)
    private void trace1() {
        log.error("心跳接受！");
    }
}

