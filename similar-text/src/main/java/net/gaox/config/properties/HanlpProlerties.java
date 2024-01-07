package net.gaox.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> hanlp 配置类 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-17 22:06
 */
@Data
@Component
@ConfigurationProperties("net.gaox.hanlp")
public class HanlpProlerties {

    /**
     * restful api url
     */
    private String url;

    /**
     * auth 凭证
     */
    private String auth;
}
