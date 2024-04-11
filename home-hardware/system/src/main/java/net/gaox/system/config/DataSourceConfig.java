package net.gaox.system.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * <p> 从nacos同步配置信息，可动态刷新 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-11 20:20
 */
@Data
@Component
public class DataSourceConfig {

    /**
     * 从nacos同步配置信息，配置动态刷新
     * Value注解同样可以，但是需要配置 spring.cloud.nacos.config.enabled=true
     */
    @NacosValue(value = "${spring.datasource.username}", autoRefreshed = true)
    public String username = "admin";

}
