package net.gaox.util.map.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p> google guava 实现的缓存 Map </p>
 *
 * @author gaox·Eric
 * @date 2024-09-19 09:32
 */
@Slf4j
public class GuavaCache {
    @SneakyThrows
    public static void main(String[] args) {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                // 设置最大容量
                .maximumSize(10)
                // 可选：设置过期时间
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        // 使用缓存
        cache.put("key1", "gaox.net");
        String value1 = cache.getIfPresent("key1");
        String value2 = cache.getIfPresent("key2");
        log.info("key1.value: {}, key2.value: {}", value1, value2);

        // TimeUnit.SECONDS.sleep(11);

        value1 = cache.getIfPresent("key1");
        value2 = cache.getIfPresent("key2");
        log.info("key1.value: {}, key2.value: {}", value1, value2);

        // 设置缓存失效
        cache.invalidate("key1");

        for (int i = 0; i < 100; i++) {
            TimeUnit.MILLISECONDS.sleep(500);
            cache.put(String.format("key%03d", i + 1), "gaox.net");
            String keys = cache.asMap().keySet().stream().sorted().collect(Collectors.joining(", "));
            log.info("cache.size: {}, keys: {}", cache.size(), keys);
        }
    }
}
