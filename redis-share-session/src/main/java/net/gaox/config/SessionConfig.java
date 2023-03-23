package net.gaox.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.nio.charset.StandardCharsets;

/**
 * <p> 配置 Redis Serializer </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 22:53
 */

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class SessionConfig {

    /**
     * 使用 FastJsonRedisSerializer 来序列化和反序列化 redis 的 value 的值
     *
     * @return RedisSerializer
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {

        // 也可以使用 GenericFastJsonRedisSerializer
        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);

        // 开放 AutoType 功能的白名单
        ParserConfig.getGlobalInstance().addAccept("net.gaox.");
        ParserConfig.getGlobalInstance().addAccept("org.springframework.");
        ParserConfig.getGlobalInstance().addAccept("org.springframework.security.core.context.");
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        serializer.setFastJsonConfig(fastJsonConfig);
        return serializer;
    }

}