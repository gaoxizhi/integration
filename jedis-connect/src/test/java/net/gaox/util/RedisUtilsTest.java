package net.gaox.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.conf.serializer.entity.Club;
import net.gaox.conf.serializer.entity.ser.ClubSerializer;
import net.gaox.util.util.KeyID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p> 原子脚本 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 18:38
 */
@Slf4j
@SpringBootTest
public class RedisUtilsTest {

    @Autowired
    JedisPool jedisPool;

    @Test
    void atomicByScript() {
        try (Jedis jedis = jedisPool.getResource()) {
            // 使用脚本原子化将两个key的值都增加1000，并返回新的值
            List<String> result = RedisUtils.atomicByScript(jedis, Arrays.asList("key1", "key2"), Collections.singletonList("1000a"));
            // 解析返回结果
            if (!CollectionUtils.isEmpty(result)) {
                log.info("Key1的新值: [{}], Key2的新值: [{}]", result.get(0), result.get(1));
            }
            String key1 = jedis.get("key1");
            log.info("jedisPool get key1 : " + key1);
        }
    }

    @Test
    void getSetKey() {
        long id = KeyID.nextId();
        String uuid = Long.toHexString(id).toUpperCase();
        log.info("jedisPool uuid : " + uuid);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(uuid, 1000, "kiss");
            log.info("jedisPool get key={}, value={}", uuid, jedis.get(uuid));
            String key1 = jedis.get("key1");
            log.info("jedisPool get key1 : " + key1);
        }
    }

    @Test
    void pipeline() {
        try (Jedis jedis = jedisPool.getResource()) {

            int group, index;
            for (group = 0; group < 100; group++) {
                log.info("Storing club with group: {}", group);
                Pipeline pipeline = jedis.pipelined();
                for (index = 0; index < 1000; index++) {
                    int id = group * 1000 + index;
                    Club club = new Club(id, "AC" + id, "米兰" + id, LocalDateTime.now(), 1);
                    String key = String.format("club:%04d:%04d", group, index);
                    byte[] jsonBytes = JSONObject.toJSONBytes(club);
                    pipeline.set(key.getBytes(), jsonBytes);
                }
                // 执行 Pipeline 中的所有命令
                pipeline.sync();
            }

            log.info("All done!");
        }
    }

    @Test
    void getSetWithSerializer() {
        try (Jedis jedis = jedisPool.getResource()) {

            String key = "club:1";
            // 定义实体对象
            Club club = new Club(1, "AC", "米兰", LocalDateTime.now(), 1);
            log.info("club:1 = {}", club);
            // 序列化
            byte[] clubBytes = ClubSerializer.serialize(club);
            jedis.set(key.getBytes(), clubBytes);
            byte[] resultBytes = jedis.get(key.getBytes());
            // 反序列化
            Club resultClub = ClubSerializer.deserialize(resultBytes);
            log.info("resultClub = {}", resultClub);
        }
    }

}