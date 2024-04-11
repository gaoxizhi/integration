package net.gaox.system.feign;

import net.gaox.domain.entity.SysKv;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <p> kv feign client </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@FeignClient(name = "base")
public interface KvFeignClient {
    @PostMapping("/kv{/key}")
    String get(@PathVariable String key);

    @GetMapping("/kv/like")
    List<SysKv> like(String key);

    @GetMapping("/kv/list")
    List<SysKv> list();

    @GetMapping(value = "/kv/echo/{string}")
    String echo(@PathVariable String string);

}
