package net.gaox.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p> 邮件服务测试 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/24 10:50
 */
@EnableScheduling
@SpringBootApplication
public class JavaMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaMailApplication.class, args);
    }
}