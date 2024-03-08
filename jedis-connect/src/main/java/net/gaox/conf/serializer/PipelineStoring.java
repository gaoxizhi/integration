package net.gaox.conf.serializer;

/**
 * <p> Pipeline 批量执行操作 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-08 16:22
 */

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.conf.serializer.entity.Club;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.time.LocalDateTime;

@Slf4j
public class PipelineStoring {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("******");

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
        // 在所有任务完成后关闭 Redis 连接
        jedis.close();
    }

}
