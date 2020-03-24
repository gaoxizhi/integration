package net.gaox.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p> 定时任务 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/24 10:52
 */
@Slf4j
//@Component
public class TimeTask {

    private final MailService mailService;

    public TimeTask(MailService mailService) {
        this.mailService = mailService;
    }

    @Scheduled(cron = "0 * * * * ? ")
    public void sendSim() {
        log.info("send to Email");
        mailService.sendSimpleMail("gx_zone@163.com", "测试spring boot mail", "测试spring boot mial 内容");
        log.info("send Email OK ");
    }
}