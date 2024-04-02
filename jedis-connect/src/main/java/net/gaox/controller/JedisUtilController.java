package net.gaox.controller;

import lombok.RequiredArgsConstructor;
import net.gaox.util.RedisUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> http 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-07 18:58
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class JedisUtilController {

    private final RedisUtils redisUtils;

    @RequestMapping("/set")
    public String set(String key, String value) {
        try {
            redisUtils.set(key, value);
            return "成功";
        } catch (Exception e) {
            return "失败";
        }
    }

    @RequestMapping("/get")
    public String get(String key) {
        try {
            Object o = redisUtils.get(key);
            return String.valueOf(o);
        } catch (Exception e) {
            return "获取出错";
        }
    }

}
