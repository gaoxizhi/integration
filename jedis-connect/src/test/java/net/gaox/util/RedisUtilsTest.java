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
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Resource
    RedisUtils redisUtils;

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
    void addStringToSet() {
        // 添加20个俩俩重复的元素到set，限制最多添加8个
        for (int i = 0; i < 20; i++) {
            String value = "value:" + (i / 2);
            boolean added = redisUtils.addStringToSet("set:gaox", value, 8);
            log.info("add to set No. [{}], value = {}, {}", i, value, added ? "success" : "fail");
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

    @Test
    void scan() {
        try (Jedis jedis = jedisPool.getResource()) {
            // 定义前缀和集合用于存储去重的键
            String prefix = "club:";
            Set<String> keys = new HashSet<>();
            List<String> list = new ArrayList<>();
            // 初始化SCAN的游标
            String cursor = ScanParams.SCAN_POINTER_START;
            log.info("task start.");
            do {
                // 配置SCAN的参数，匹配"club:"开头的键，并一次性返回100个结果
                ScanParams scanParams = new ScanParams().match(prefix + "*").count(1000);
                ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
                // 将结果添加到Set中，Set会自动去重
                keys.addAll(scanResult.getResult());
                list.addAll(scanResult.getResult());

                // 更新游标
                cursor = scanResult.getCursor();
            } while (!cursor.equals(ScanParams.SCAN_POINTER_START));
            log.info("task end.");

            // 关闭Jedis连接
            jedis.close();

            // 打印去重后的键
            log.info("Unique keys starting with 'club:', count = {}, all:", keys.size());

            // 每批次处理100个元素
            int batchSize = 100;
            List<String> distinct = list.stream().distinct().collect(Collectors.toList());
            // 通过流式处理进行分批输出
            IntStream.range(0, (distinct.size() + batchSize - 1) / batchSize)
                    .mapToObj(i -> distinct.subList(i * batchSize, Math.min((i + 1) * batchSize, distinct.size())))
                    .forEach(batch -> log.info("values = {}", String.join(",\t", batch)));
        }
    }

}