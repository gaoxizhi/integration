package net.gaox.system.controller;

import lombok.RequiredArgsConstructor;
import net.gaox.domain.entity.SysKv;
import net.gaox.domain.model.dto.KvQuery;
import net.gaox.system.config.NacosConfig;
import net.gaox.system.feign.KvFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <p> 远程调用测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-11 21:50
 */
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final RestTemplate restTemplate;
    private final KvFeignClient kvFeignClient;
    private final NacosConfig nacosConfig;

    private final String baseUrl = "http://base/kv";

    @GetMapping(value = "/kv/{key}")
    public String kv(@PathVariable String key) {
        String url = baseUrl + "/name/{key}";
        String result = restTemplate.getForObject(url, String.class, key);
        return result;
    }

    @GetMapping("/like")
    public List<SysKv> like(String key) {
        String url = baseUrl + "/like?key={key}";
        HashMap<String, Object> map = new HashMap<String, Object>() {{
            put("key", key);
        }};
        List<SysKv> list = restTemplate.getForObject(url, List.class, map);

        return Optional.ofNullable(list).orElse(new ArrayList<>());
    }

    @GetMapping("/list")
    public List<SysKv> list() {
        String url = baseUrl + "/list";
        List<SysKv> list = restTemplate.getForObject(url, List.class);

        return Optional.ofNullable(list).orElse(new ArrayList<>());
    }

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://base/kv/echo/" + str, String.class);
    }

    @GetMapping(value = "/feign/kv/{key}")
    public String feignKv(@PathVariable String key) {
        String result = kvFeignClient.get(key);
        return result;
    }

    @GetMapping("/feign/like")
    public List<SysKv> feignLike(String key) {
        List<SysKv> list = kvFeignClient.like(key);

        return Optional.ofNullable(list).orElse(new ArrayList<>());
    }

    @GetMapping("/feign/list")
    public List<SysKv> feignList(KvQuery query) {
        List<SysKv> list = kvFeignClient.list(query);

        return Optional.ofNullable(list).orElse(new ArrayList<>());
    }

    @GetMapping(value = "/feign/echo/{str}")
    public String feignEcho(@PathVariable String str) {
        String echo = kvFeignClient.echo(str);
        return echo;
    }

    @GetMapping("/get/config/username")
    public String getNacosConfig() {
        return nacosConfig.getUsername();
    }

}
