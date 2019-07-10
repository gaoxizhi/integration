package net.gaox.relation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: <p>  </p>
 * @ClassName: App
 * @Author: gaox·Eric
 * @Date: 2019/7/10 17:20
 */

@SpringBootApplication
@MapperScan({"net.gaox.relation"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
