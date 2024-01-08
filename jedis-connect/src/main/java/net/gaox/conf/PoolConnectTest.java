package net.gaox.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p> jedis 连接池测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-08 10:38
 */
public class PoolConnectTest {

    private static final Logger log = LoggerFactory.getLogger(PoolConnectTest.class);

    public static void main(String[] args) {
        // scheme   = redis://
        // username = clint
        // password = pass
        // host     = gaox.net
        // port     = 6379
        // database = 0
        JedisPool jedisPool = new JedisPool("redis://clint:pass@gaox.net:6379/0");
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
