package net.gaox.page.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: <p>  </p>
 * @ClassName WebMVC
 * @author gaox·Eric
 * @date 2019/4/18 22:20
 */
public class WebMVC implements WebMvcConfigurer {

    /**
     * JSON序列化 用于将controller返回的实体类转换成json串
     */
    @Bean
    public FastJsonHttpMessageConverter jsonConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteNullBooleanAsFalse
                , SerializerFeature.WriteNullNumberAsZero
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNonStringKeyAsString
                , SerializerFeature.DisableCircularReferenceDetect
        );
        converter.setFastJsonConfig(config);
        return converter;
    }

}
