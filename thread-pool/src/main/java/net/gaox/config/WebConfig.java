package net.gaox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> web 配置 </p>
 *
 * @author gaox·Eric
 * @date 2024-03-21 13:07
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 对所有路径生效
        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**");
    }

}
