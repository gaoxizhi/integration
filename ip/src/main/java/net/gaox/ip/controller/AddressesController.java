package net.gaox.ip.controller;

import lombok.extern.slf4j.Slf4j;
import net.gaox.ip.annotation.MonitorIP;
import net.gaox.ip.entry.Addresses;
import net.gaox.ip.model.AddressesXmlVO;
import net.gaox.ip.util.AddressUtil;
import net.gaox.ip.util.HttpContextUtil;
import net.gaox.ip.util.IpUtil;
import net.gaox.ip.util.XmlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/29 18:21
 */
@Slf4j
@RestController
public class AddressesController {

    @MonitorIP
    @GetMapping("/")
    public String getIpString(HttpServletRequest request) {
        final Addresses info = IpUtil.getAddressesInfo(request);
        log.info("查询ip[{}]，地理位置[{}-{}-{}]。ip方式", info.getIp(), info.getCountry(), info.getRegion(), info.getCity());
        return info.getIp();
    }

    @MonitorIP
    @GetMapping(value = "/json")
    public Addresses getJson(HttpServletRequest request) {
        final Addresses info = IpUtil.getAddressesInfo(request);
        log.info("查询ip[{}]，地理位置[{}-{}-{}]。json方式", info.getIp(), info.getCountry(), info.getRegion(), info.getCity());
        return info;
    }

    @MonitorIP
    @GetMapping(value = "/xml")
    public String getIp(HttpServletRequest request) {
        Addresses info = IpUtil.getAddressesInfo(request);
        log.info("查询ip[{}]，地理位置[{}-{}-{}]。xml方式", info.getIp(), info.getCountry(), info.getRegion(), info.getCity());
        AddressesXmlVO xmlVO = new AddressesXmlVO();
        BeanUtils.copyProperties(info, xmlVO);
        return XmlUtil.convertToXml(xmlVO);
    }

    @GetMapping("/ip")
    public String getCityInfo() {
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IpUtil.getIpAddress(request);
        String cityInfo = AddressUtil.getCityInfo(ip);
        log.info(MessageFormat.format("当前IP为:[{0}]；当前IP地址解析出来的地址为:[{1}]", ip, cityInfo));
        return cityInfo;
    }

}
