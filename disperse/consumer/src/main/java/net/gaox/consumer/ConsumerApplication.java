package net.gaox.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p> 启动类 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-13 20:20:00
 */
@SpringBootApplication(scanBasePackages = "net.gaox")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
