package net.gaox.aliyunoss.config;

import lombok.Data;

/**
 * @author gaox·Eric
 * @date 2019/4/18 23:02
 */
@Data
public class ParamConfig {
    /**
     * 注意：这里记录的是自己的Oss账号信息
     */
    public static String endpoint;
    public static String accessKeyId;
    public static String accessKeySecret;
    public static String bucketName;
    public static String folder;
}