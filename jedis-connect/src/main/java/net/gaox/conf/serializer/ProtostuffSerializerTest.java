package net.gaox.conf.serializer;

import net.gaox.conf.serializer.entity.Club;
import net.gaox.conf.serializer.entity.ser.ClubSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;

/**
 * <p> Protostuff 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 17:34
 */
public class ProtostuffSerializerTest {

    private static final Logger log = LoggerFactory.getLogger(ProtostuffSerializerTest.class);

    public static void main(String[] args) {

        // 1. 生成一个Jedis对象，这个对象负责和指定Redis实例进行通信
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("******");

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
        jedis.close();
    }
}
