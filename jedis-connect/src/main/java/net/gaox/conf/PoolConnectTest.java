package net.gaox.conf;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p> jedis 连接池测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-08 10:38
 */
public class PoolConnectTest {

    private static final Logger log = LoggerFactory.getLogger(PoolConnectTest.class);

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

        // scheme   = redis://
        // username = client
        // password = pass
        // host     = gaox.net
        // port     = 6379
        // database = 0
        // JedisPool jedisPool = new JedisPool(poolConfig, "redis://client:pass@gaox.net:6379/0");
        JedisPool jedisPool = new JedisPool(poolConfig, "gaox.net", 6379, 3000,
                "pass", 0, "client");

        Jedis jedis = null;
        try {
            // 1. 从连接池获取jedis对象
            jedis = jedisPool.getResource();
            // 2. 执行操作
            String hello = jedis.get("hello");
            log.info("get hello = {}", hello);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 如果使用 JedisPool ，close 操作不是关闭连接，代表归还连接池
                jedis.close();
            }
        }
    }
}
