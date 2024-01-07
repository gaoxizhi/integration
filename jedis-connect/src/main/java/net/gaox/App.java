package net.gaox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <p> 启动类 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */

@EnableCaching
@SpringBootApplication(scanBasePackages = "net.gaox")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
