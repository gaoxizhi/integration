package net.gaox.system.feign;

import net.gaox.domain.entity.SysKv;
import net.gaox.domain.model.dto.KvQuery;
import net.gaox.system.feign.fallback.KvFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p> kv feign client </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@FeignClient(name = "base", path = "/kv", fallbackFactory = KvFeignClientFallbackFactory.class)
public interface KvFeignClient {
    @GetMapping("/name/{key}")
    String get(@PathVariable("key") String key);

    @GetMapping("/like")
    List<SysKv> like(@RequestParam("key") String key);

    @GetMapping("/list")
    List<SysKv> list(@SpringQueryMap KvQuery kvQuery);

    @GetMapping(value = "/echo/{string}")
    String echo(@PathVariable("string") String string);

}
