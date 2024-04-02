package net.gaox.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p> 分布式锁Redis实现 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-23 11:32
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisDistributedLock {

    private static final String REDIS_LOCK_PREFIX = "lock:";

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean acquireLock(String lockKey, String clientId, int expireTime) {
        String key = REDIS_LOCK_PREFIX + lockKey;
        log.info("尝试获得分布式锁:[{}]，clintId:[{}], expireTime:[{}]", key, clientId, expireTime);
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, clientId, expireTime, TimeUnit.SECONDS);
        log.info("获得分布式锁:[{}]，clintId:[{}], 状态: {}", key, clientId, Boolean.TRUE.equals(result) ? "成功" : "失败");
        return Boolean.TRUE.equals(result);
    }

    public Boolean releaseLock(String lockKey, String clientId) {

        String key = REDIS_LOCK_PREFIX + lockKey;
        log.info("尝试释放分布式锁:[{}]，clintId:[{}], ", key, clientId);
        String currentValue = redisTemplate.opsForValue().get(key);
        if (currentValue != null) {

            if (currentValue.equals(clientId)) {
                // 仅当锁当前由请求客户端持有时才释放
                Boolean delete = redisTemplate.delete(key);
                if (Boolean.TRUE.equals(delete)) {
                    log.info("成功释放分布式锁:[{}]，clintId:[{}]", key, clientId);
                } else {
                    log.warn("释放分布式锁失败:[{}]，clintId:[{}]", key, clientId);
                }
                return Boolean.TRUE.equals(delete);
            } else {
                log.info("分布式锁被其他应用持有:[{}]，clintId:[{}]", key, clientId);
            }
        } else {
            log.info("不存在分布式锁:[{}]，clintId:[{}]", key, clientId);
        }
        return false;
    }

}
