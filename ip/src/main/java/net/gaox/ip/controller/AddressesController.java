package net.gaox.ip.controller;

import net.gaox.ip.entry.Addresses;
import net.gaox.ip.util.IpUtil;
import net.gaox.ip.util.XmlUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/29 18:21
 */
@RestController
public class AddressesController {

    @GetMapping("/")
    public String getIpString(HttpServletRequest request) {
        return IpUtil.getAddressesInfo(request).getIp();
    }

    @GetMapping(value = "/json", produces = {"application/json;charset=utf-8"})
    public Addresses getJson(HttpServletRequest request) {
        return IpUtil.getAddressesInfo(request);
    }

    @GetMapping(value = "/xml", produces = {"application/xml;charset=utf-8"})
    public String getIp(HttpServletRequest request) {
        Addresses addressesInfo = IpUtil.getAddressesInfo(request);
        return XmlUtil.convertToXml(addressesInfo);
    }
}