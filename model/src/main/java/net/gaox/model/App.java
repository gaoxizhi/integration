package net.gaox.model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gaox·Eric
 */
@SpringBootApplication
@MapperScan({"net.gaox.model.mapper"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}