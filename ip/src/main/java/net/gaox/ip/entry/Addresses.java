package net.gaox.ip.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;

/**
 * <p>  </p>
 * XmlAccessorType 表示使用这个类中的 private 非静态字段作为 XML 的序列化的属性或者元素。
 * XmlAccessType 选择属性，也就是使用 set/get 方法来序列化属性或者元素。
 * XmlRootElement 根节点
 * XmlType.propOrder 指定输出顺序
 *
 * @author gaox·Eric
 * @date 2019/10/29 18:23
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "addresses")
@XmlType(propOrder = {"ip", "country", "region", "city"})
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Addresses {
    /**
     * ip地址 : 115.220.204.21
     */
    @XmlElement(required = true)
    private String ip;
    /**
     * 国家 : 中国
     */
    @XmlElement(required = true)
    private String country;
    /**
     * 国家识别码
     * JSONField(name = "country_id")
     */
    private String countryId;
    /**
     * 省份 : 浙江
     */
    @XmlElement(required = true)
    private String region;

    private String regionId;
    /**
     * 城市
     */
    @XmlElement(required = true)
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