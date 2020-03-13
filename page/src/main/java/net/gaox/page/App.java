package net.gaox.page;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gaox·Eric
 * @date 2019/7/14 22:32
 */
@SpringBootApplication
@MapperScan("net.gaox.page.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}