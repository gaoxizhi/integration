package net.gaox.redis.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Description: <p>  </p>
 * @ClassName MyCacheGenerator
 * @Author: gaox·Eric
 * @Date: 2019/4/13 19:02
 */
@Configuration
public class MyCacheGenerator {
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... params) {
                return method.getName() + "[" + Arrays.asList(params).toString() + "]";
            }
        };
    }
}
