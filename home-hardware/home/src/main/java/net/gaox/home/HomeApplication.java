package net.gaox.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p> 启动类 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-13 20:20:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({"net.gaox.home.mapper"})
public class HomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeApplication.class, args);
    }

}
