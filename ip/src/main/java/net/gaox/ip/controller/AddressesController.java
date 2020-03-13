package net.gaox.ip.controller;

import lombok.extern.slf4j.Slf4j;
import net.gaox.ip.entry.Addresses;
import net.gaox.ip.model.AddressesXmlVO;
import net.gaox.ip.util.IpUtil;
import net.gaox.ip.util.XmlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/29 18:21
 */
@Slf4j
@RestController
public class AddressesController {

    @GetMapping("/")
    public String getIpString(HttpServletRequest request) {
        final Addresses info = IpUtil.getAddressesInfo(request);
        log.info("查询ip[{}]，地理位置[{}-{}-{}]。ip方式", info.getIp(), info.getCountry(), info.getRegion(), info.getCity());
        return info.getIp();
    }

    @GetMapping(value = "/json")
    public Addresses getJson(HttpServletRequest request) {
        final Addresses info = IpUtil.getAddressesInfo(request);
        log.info("查询ip[{}]，地理位置[{}-{}-{}]。json方式", info.getIp(), info.getCountry(), info.getRegion(), info.getCity());
        return info;
    }

    @GetMapping(value = "/xml")
    public String getIp(HttpServletRequest request) {
        Addresses info = IpUtil.getAddressesInfo(request);
        log.info("查询ip[{}]，地理位置[{}-{}-{}]。xml方式", info.getIp(), info.getCountry(), info.getRegion(), info.getCity());
        AddressesXmlVO xmlVO = new AddressesXmlVO();
        BeanUtils.copyProperties(info, xmlVO);
        return XmlUtil.convertToXml(xmlVO);
    }
}