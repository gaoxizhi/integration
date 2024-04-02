package net.gaox.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> minio配置 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 23:30
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * 服务地址
     */
    private String endpoint;

    /**
     * 认证账户
     */
    private String accessKey;

    /**
     * 认证密码
     */
    private String secretKey;

    /**
     * 桶名称, 优先级最低
     */
    private String bucket = "gaox";

    /**
     * 桶不在的时候是否新建桶
     */
    private boolean createBucket = true;

    /**
     * 启动的时候检查桶是否存在
     */
    private boolean checkBucket = true;

    /**
     * 设置HTTP连接超时。单位毫秒，值为0意味着没有超时
     */
    private long connectTimeout = 5_000L;

    /**
     * 设置HTTP写入超时。单位毫秒，值为0意味着没有超时
     */
    private long writeTimeout = 0L;

    /**
     * 设置HTTP读取超时。单位毫秒，值为0意味着没有超时
     */
    private long readTimeout = 0L;

}
