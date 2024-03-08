package net.gaox.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p> jedis 连接测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 15:08
 */
public class ConnectTest {
    private static final Logger log = LoggerFactory.getLogger(ConnectTest.class);

    public static void main(String[] args) {

        // 1. 生成一个Jedis对象，这个对象负责和指定Redis实例进行通信
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("******");

        // 2. jedis执行set操作
        String setResult = jedis.set("hello", "world");
        log.info(setResult);

        // 3. jedis执行get操作, value="world"
        String value = jedis.get("hello");
        log.info(value);

        Long counter = jedis.incr("counter");
        log.info("counter = {}", counter);

        // hash
        jedis.hset("hash", "f1", "v1");
        jedis.hset("hash", "f2", "v2");
        Map<String, String> hash = jedis.hgetAll("hash");
        log.info("hash = {}", hash);

        // list
        jedis.rpush("list", "1");
        jedis.rpush("list", "2");
        jedis.rpush("list", "3");
        List<String> list = jedis.lrange("list", 0, -1);
        log.info("list = {}", String.join(", ", list));

        // set
        jedis.sadd("set", "a");
        jedis.sadd("set", "b");
        jedis.sadd("set", "a");
        Set<String> set = jedis.smembers("set");
        log.info("set = {}", String.join(", ", set));

        // zset
        jedis.zadd("zset", 99, "tom");
        jedis.zadd("zset", 66, "peter");
        jedis.zadd("zset", 33, "james");
        Set<Tuple> zset = jedis.zrangeWithScores("zset", 0, -1);
        log.info("zset = {}", zset);

        // 获取所有keys
        Set<String> keys = jedis.keys("*");
        log.info("all keys = {}", String.join(", ", keys));

        // 生成 pipeline 对象
        Pipeline pipeline = jedis.pipelined();
        // pipeline 执行命令，注意此时命令并未真正执行
        pipeline.del("list");
        pipeline.del("hash");
        // 执行命令
        pipeline.incr("counter");
        List<Object> resultList = pipeline.syncAndReturnAll();
        for (Object object : resultList) {
            log.info("return = {}", object);
        }

        jedis.close();
    }
}
