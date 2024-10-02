package net.gaox.ip.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 解析 ip信息
     *
     * @param context 格式文本
     * @return ip信息
     */
    public static Addresses parserByContext(String context) {
        if (StringUtils.isBlank(context)) {
            return null;
        }
        String[] split = context.split("\\|");
        Addresses ipInfo = new Addresses();

        if (!"0".equals(split[0])) {
            ipInfo.setCountry(split[0]);
        }
        if (!"0".equals(split[1])) {
            ipInfo.setArea(split[1]);
        }
        if (!"0".equals(split[2])) {
            ipInfo.setRegion(split[2]);
        }
        if (!"0".equals(split[3])) {
            ipInfo.setCity(split[3]);
        }
        if (!"0".equals(split[4])) {
            ipInfo.setIsp(split[4]);
        }
        return ipInfo;
    }

    public static Addresses parserByContext(String context, String ip) {

        Addresses ipInfo = parserByContext(context);
        if (null == ipInfo) {
            return null;
        }
        if (StringUtils.isNotBlank(ip)) {
            return ipInfo.setIp(ip);
        }
        return ipInfo;
    }

    public String getInfo() {
        return "ip: " + ip + ", 国家: " + country + ", 省份: " + region + ", 城市: " + city + ", 县: " + county + ", 运营商: " + isp;
    }

}