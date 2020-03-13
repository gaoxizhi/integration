package net.gaox.ip.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/29 18:23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Addresses {
    /**
     * ip地址 : 115.220.204.21
     */
    private String ip;
    /**
     * 国家 : 中国
     */
    private String country;
    /**
     * 国家识别码
     * JSONField(name = "country_id")
     */
    private String countryId;
    /**
     * 省份 : 浙江
     */
    private String region;
    private String regionId;
    /**
     * 城市
     */
    private String city;
    private String cityId;
    /**
     * 县
     */
    private String county;
    private String countyId;
    /**
     * 地域
     */
    private String area;
    private String areaId;
    /**
     * 网络服务提供者（Internet Service Provider）
     */
    private String isp;
    private String ispId;
}