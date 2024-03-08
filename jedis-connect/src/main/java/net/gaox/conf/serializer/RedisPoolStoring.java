package net.gaox.conf.serializer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.conf.serializer.entity.Club;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p> 使用线程池，加快存储速度 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-08 16:33
 */
@Slf4j
public class RedisPoolStoring {

    /**
     * 使用 JedisPool 替换 Jedis 实例
     */
    private static JedisPool jedisPool;

    public static void main(String[] args) {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        // 设置最大连接数为默认值的5倍
        poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
        // 设置最大空闲连接数为默认值的3倍
        poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
        // 设置最小空闲连接数为默认值的2倍
        poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
        // 设置开启jmx功能
        poolConfig.setJmxEnabled(true);
        // 设置连接池没有连接后客户端的最大等待时间(单位为毫秒)
        poolConfig.setMaxWaitMillis(3000);

        jedisPool = new JedisPool(poolConfig, "gaox.net", 6379, 3000, "pass", 0, "client");

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        log.info("start");
        for (int i = 0; i < 100_000; i++) {
            threadPool.submit(new StoringTask(i));
        }
        log.info("complete submit");
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ie) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // 关闭 Redis 连接池
        jedisPool.close();
        log.info("close");
    }

    /**
     * 存入任务
     */
    private static class StoringTask implements Runnable {
        private final int index;

        public StoringTask(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            // 从连接池获取 Jedis 实例
            try (Jedis jedis = jedisPool.getResource()) {
                Club club = new Club(index, "AC" + index, "米兰" + index, LocalDateTime.now(), 1);
                String key = String.format("club:%04d:%04d", index / 1000, index % 1000);
                byte[] jsonBytes = JSONObject.toJSONBytes(club);
                jedis.set(key.getBytes(), jsonBytes);
            } catch (Exception e) {
                log.info("Error storing club with ID: {}", index);
                log.error("Error:", e);
            }
        }
    }

}
