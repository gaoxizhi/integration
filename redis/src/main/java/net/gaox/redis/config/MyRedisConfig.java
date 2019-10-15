package net.gaox.redis.config;

import net.gaox.redis.entity.Home;
import net.gaox.redis.entity.SomaTempLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author gaox·Eric
 * @date 2019/4/14 01:22
 */
@Configuration
public class MyRedisConfig {

//    使用redis自带的jackson序列化器
//    boot 版本  1.5.19.RELEASE

    //

    /**
     * Priamry 主要模板
     * home类型模板
     *
     * @param redisConnectionFactory
     */
    @Primary
    @Bean
    public RedisTemplate<Object, Home> homeRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Home> template = new RedisTemplate<Object, Home>();
        template.setConnectionFactory(redisConnectionFactory);
        final FastJsonRedisSerializer<Home> homeJson = new FastJsonRedisSerializer<>();
        template.setDefaultSerializer(homeJson);
        return template;
    }
    //
//    /**
//     * CachemMnagerCustoizers 可以定制缓存的一些规则
//     *
//     * @param homeRedisTemplate
//     * @return
//     */
//    @Primary
//    @Bean
//    public RedisCacheManager homeCacheManager(RedisTemplate<Object, Home> homeRedisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(homeRedisTemplate);
//        //设置一个前缀，cacheName作为前缀
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }
//

    /**
     * 类型模型
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, SomaTempLog> somaRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, SomaTempLog> template = new RedisTemplate<Object, SomaTempLog>();
        template.setConnectionFactory(redisConnectionFactory);
        final FastJsonRedisSerializer<SomaTempLog> somaJson = new FastJsonRedisSerializer<SomaTempLog>();
        template.setDefaultSerializer(somaJson);
        return template;
    }
//
//
//    @Bean
//    public RedisCacheManager somaCacheManager(RedisTemplate<Object, SomaTempLog> somaRedisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(somaRedisTemplate);
//        //设置一个前缀，cacheName作为前缀
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }
    //使用fastjson作为默认序列化器

    /**
     * RedisCacheConfiguration 为redisCache的自定义设置类 他和上文提高的 RedisCacheConfiguration 不是一个类，
     * 前者所在包为org.springframework.data.redis.cache，
     * 后者所在包为 org.springframework.boot.autoconfigure.cache
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager gaoxCacheManager(RedisConnectionFactory redisConnectionFactory) {
        //创建自定义序列化器
        FastJsonRedisSerializer jsonSeria = new FastJsonRedisSerializer();
        //包装成SerializationPair类型
        RedisSerializationContext.SerializationPair serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(jsonSeria);
        //redis默认配置文件,并且设置过期时间
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1));
        //设置序列化器
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(serializationPair);
        //RedisCacheManager 生成器创建
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration);
        return builder.build();
    }
}