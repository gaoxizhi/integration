package net.gaox.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;
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
    private final RedisTemplate<String, String> redisStringTemplate;

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

    private static final String SCRIPT =
            "redis.call('SADD', KEYS[1], ARGV[1]) " +
                    "if redis.call('SCARD', KEYS[1]) > tonumber(ARGV[2]) then " +
                    "    redis.call('SREM', KEYS[1], ARGV[1]) " +
                    "    return 0 " +
                    "else " +
                    "    return 1 " +
                    "end";

    /**
     * 最多添加指定个元素到set集合
     *
     * @param key     set集合的key
     * @param value   元素
     * @param maxSize 最大元素个数
     * @return 是否添加成功
     */
    public boolean addStringToSet(String key, String value, Integer maxSize) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(SCRIPT);
        redisScript.setResultType(Long.class);
        // tonumber 转换为数字类型时，不指定为string时，传入值是带（"）的，解析报错
        Object result = redisStringTemplate.execute(redisScript, Collections.singletonList(key), value, String.valueOf(maxSize));
        if (result instanceof Long) {
            return Long.valueOf(1L).equals(result);
        }
        return false;
    }

}
