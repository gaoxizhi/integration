package net.gaox.notify.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p> redis 序列化配置 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-23 00:26
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        lettuceConnectionFactory.setShareNativeConnection(false);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //使用FastJsonRedisSerializer替换默认的JdkSerializationRedisSerializer来序列化和反序列化redis的value值
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // value序列化方式采用fastJson
        template.setValueSerializer(stringRedisSerializer);

        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用fastJson
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
