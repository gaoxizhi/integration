package net.gaox.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p> JedisUtil </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 18:28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    // 设置带过期时间的缓存
    public Boolean set(String key, Object value, long time) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
    }

    // 设置缓存
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 根据 key 获得缓存
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 根据 key 删除缓存
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    // 根据 keys 集合批量删除缓存
    public Long delete(Set<String> keys) {
        return redisTemplate.delete(keys);
    }

    // 根据正则表达式匹配 keys 获取缓存
    public Set<String> getKeysByPattern(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void hmput(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Boolean expire(String key, long timeOut) {
        return redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
    }

    /**
     * 脚本原子性 赋值给两个key相同的值
     *
     * @param jedis 连接
     * @param keys  keys
     * @param args  value
     * @return result
     */
    public static List<String> atomicByScript(Jedis jedis, List<String> keys, List<String> args) {
        // Lua脚本常量化，提高可读性和可维护性
        // local key1 = KEYS[1]; local key2 = KEYS[2];
        // local incrementValue = tonumber(ARGV[1]);
        // if not incrementValue then error('invalid incrementValue');
        // end
        // redis.call('incrby', key1, incrementValue);
        // redis.call('incrby', key2, incrementValue);
        // return {redis.call('get', key1), redis.call('get', key2)}
        final String script = "local key1 = KEYS[1]; local key2 = KEYS[2]; local incrementValue = tonumber(ARGV[1]); " +
                "if not incrementValue then error('invalid incrementValue'); end " +
                "redis.call('incrby', key1, incrementValue); redis.call('incrby', key2, incrementValue); " +
                "return {redis.call('get', key1), redis.call('get', key2)}";

        List<String> result = new ArrayList<>();
        try {
            // 使用泛型提高类型安全
            Object evalResult = jedis.eval(script, keys, args);
            if (evalResult instanceof List) {
                result = (List<String>) evalResult;
            } else {
                log.error("Unexpected type of script execution result: {}", evalResult.getClass().getName());
            }
        } catch (Exception e) {
            // 异常处理，例如网络异常、脚本执行错误等
            log.error("Error executing the script: {}", e.getMessage());
        }

        log.debug("result = {}", result);
        return result;
    }

}
