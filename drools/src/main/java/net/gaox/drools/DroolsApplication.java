package net.gaox.drools;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author gaox
 */
@Configuration
@SpringBootApplication
public class DroolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroolsApplication.class, args);
    }

    /**
     * JSON序列化 用于将controller返回的实体类转换成json串
     *
     * @return
     */
    @Bean
    public FastJsonHttpMessageConverter jsonConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();

        config.setCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        config.setSerializerFeatures(
                SerializerFeature.WriteNullBooleanAsFalse
                , SerializerFeature.WriteNullNumberAsZero
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNonStringKeyAsString
                , SerializerFeature.DisableCircularReferenceDetect
                // , SerializerFeature.WriteEnumUsingName
                // 设置Enum输出ToString内容
                , SerializerFeature.WriteEnumUsingToString
        );
        converter.setFastJsonConfig(config);
        return converter;
    }
}