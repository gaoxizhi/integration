package net.gaox.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "net.gaox.redis")
@EnableCaching
public class RedisApplication {

    public static void main(String[] args) {
        //实现redis配置fastjson
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        SpringApplication.run(RedisApplication.class, args);
    }

}

