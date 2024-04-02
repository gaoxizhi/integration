package net.gaox.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.service.KeyService;
import net.gaox.util.RedisDistributedLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p> 接口测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-23 00:35
 */
@Slf4j
@RestController
@AllArgsConstructor
public class Controller {
    private final KeyService keyService;
    private final RedisTemplate<String, String> redisTemplate;

    @GetMapping("/set")
    public String set(String key, String value) {
        keyService.addToRedis(key, value);
        return "ok";
    }

    @GetMapping
    public String hello(String key) {
        Object value = keyService.getFromRedis(key);
        return String.valueOf(value);
    }

    @GetMapping("/setnx")
    public String setnx(String key, String value, Integer timeout) {
        String result = keyService.setnx(key, value, timeout);
        return result;
    }

    @GetMapping("/distributedLock")
    public String distributedLock() throws InterruptedException {
        String lockName = "myLock";

        RedisDistributedLock redisLock = new RedisDistributedLock(redisTemplate);
        String clientId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        boolean lockAcquired = redisLock.acquireLock(lockName, clientId, 30);
        if (lockAcquired) {
            // 执行临界区代码
            TimeUnit.SECONDS.sleep(20);
            redisLock.releaseLock(lockName, clientId);
        }
        return Boolean.TRUE.equals(lockAcquired) ? "ok" : "fail";
    }

}
