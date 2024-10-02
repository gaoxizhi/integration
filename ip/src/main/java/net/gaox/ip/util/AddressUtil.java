package net.gaox.ip.util;

import lombok.extern.slf4j.Slf4j;
import net.gaox.ip.entry.Addresses;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * <p> 获取IP城市信息工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-02 23:15
 */
@Slf4j
public class AddressUtil {

    private static Searcher SEARCHER = null;

    static {
        try {
            String dbPath = "ip2region/ip2region.xdb";
            File file = ResourceUtils.getFile("classpath:" + dbPath);
            SEARCHER = Searcher.newWithFileOnly(file.getAbsolutePath());
            log.info("bean [{}]", SEARCHER);
        } catch (Exception e) {
            log.error("init ip region error", e);
        }

    }

    /**
     * 根据IP地址查询IP城市信息
     *
     * @param ip IP地址
     * @return IP城市信息
     */
    public static String getCityInfo(String ip) {
        try {
            // region的格式为 国家|区域|省份|城市|ISP，缺省的地域信息默认是0
            return SEARCHER.search(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认返回空字符串
        return "";
    }

    public static Addresses getAddresses(String ip) {
        try {
            String search = SEARCHER.search(ip);
            return Addresses.parserByContext(search, ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
