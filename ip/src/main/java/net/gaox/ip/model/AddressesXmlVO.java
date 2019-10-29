package net.gaox.ip.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>  </p>
 * XmlAccessorType 表示使用这个类中的 private 非静态字段作为 XML 的序列化的属性或者元素。
 * XmlAccessType 选择属性，也就是使用 set/get 方法来序列化属性或者元素。
 * XmlRootElement 根节点
 * XmlType.propOrder 指定输出顺序
 * XmlElement(required = true) 当XmlAccessType设置为NONE时，指定输出的属性
 *
 * @author gaox·Eric
 * @date 2019/10/29 20:08
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "addresses")
@XmlType(propOrder = {"ip", "country", "region", "city"})
@Data
public class AddressesXmlVO {
    /**
     * ip地址 : 115.220.204.21
     */

    private String ip;
    /**
     * 国家 : 中国
     */
    private String country;
    /**
     * 省份 : 浙江
     */
    private String region;
    /**
     * 城市
     */
    private String city;
}