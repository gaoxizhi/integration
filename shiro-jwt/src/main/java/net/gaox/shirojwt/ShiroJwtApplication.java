package net.gaox.shirojwt;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>  </p>
 *
 * @author: gaox·Eric
 * @date:
 */
@EnableScheduling
@SpringBootApplication
public class ShiroJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtApplication.class, args);
    }

    /**
     * JSON序列化 用于将controller返回的实体类转换成json串
     */
    @Bean
    public FastJsonHttpMessageConverter jsonConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.DisableCircularReferenceDetect);
        converter.setFastJsonConfig(config);
        return converter;
    }
}
