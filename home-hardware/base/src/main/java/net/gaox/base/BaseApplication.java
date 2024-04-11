package net.gaox.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p> 启动类 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-13 20:20:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"net.gaox.base.mapper"})
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

}
