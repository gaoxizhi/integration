package net.gaox.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p> RedisTemplate 代理 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-23 00:28
 */
@Slf4j
@Service
public class KeyService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public KeyService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToRedis(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public String setnx(String key, String value, Integer timeout) {
        log.info("设置键值 key:[{}]，value:[{}], timeout:[{}]", key, value, timeout);
        Boolean set = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
        String result = Boolean.TRUE.equals(set) ? "成功" : "失败";
        log.info("设置{}", result);
        return result;
    }

}
