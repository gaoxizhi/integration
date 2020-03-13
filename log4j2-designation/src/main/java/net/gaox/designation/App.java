package net.gaox.designation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p> 日志分离设置 </p>
 *
 * @author gaox·Eric
 * @date 2019/10/17 00:30
 */
@EnableScheduling
@SpringBootApplication
@MapperScan({"net.gaox.designation.mapper"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}