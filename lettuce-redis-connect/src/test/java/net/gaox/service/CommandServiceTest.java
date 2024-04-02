package net.gaox.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Random;

/**
 * <p> 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-23 11:48
 */
@Slf4j
@SpringBootTest
public class CommandServiceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private KeyService keyService;

    @Test
    void setKey() {
        KeyService keyService = applicationContext.getBean(KeyService.class);
        // 进行测试
        Random random = new Random();
        int nextInt = random.nextInt(200_000);
        String value = String.valueOf(nextInt);
        keyService.addToRedis("key", value);
        Object getValue = keyService.getFromRedis("key");
        log.info("getValue:{}", getValue);
    }

    @Test
    void setKeyNamed() {
        Random random = new Random();
        int nextInt = random.nextInt(200_000);
        String value = String.valueOf(nextInt);
        keyService.addToRedis("keyNamed", value);
        Object getValue = keyService.getFromRedis("keyNamed");
        log.info("getValue:{}", getValue);
    }

    @Test
    void setnx() {
        Random random = new Random();
        int nextInt = random.nextInt(200_000);
        String value = String.valueOf(nextInt);
        keyService.setnx("keyNamed", value, 20);
        Object getValue = keyService.getFromRedis("keyNamed");
        log.info("getValue:{}", getValue);
    }

}
