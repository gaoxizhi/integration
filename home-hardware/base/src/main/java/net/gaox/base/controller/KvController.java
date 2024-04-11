package net.gaox.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import net.gaox.base.service.SysKvService;
import net.gaox.domain.entity.SysKv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p> SysKv 前端控制器 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@RestController
@RequestMapping("/kv")
@RequiredArgsConstructor
public class KvController {
    private final SysKvService sysKvService;

    @GetMapping("/{key}")
    public String get(@PathVariable String key) {
        LambdaQueryWrapper<SysKv> queryWrapper = new LambdaQueryWrapper<SysKv>().eq(SysKv::getK, key);
        SysKv one = sysKvService.getOne(queryWrapper, false);
        return Optional.ofNullable(one).isPresent() ? one.getV() : null;
    }

    @GetMapping("/like")
    public List<SysKv> like(String key) {
        LambdaQueryWrapper<SysKv> queryWrapper = new LambdaQueryWrapper<SysKv>().likeLeft(SysKv::getK, key);
        List<SysKv> list = sysKvService.list(queryWrapper);

        return Optional.ofNullable(list).orElse(new ArrayList<>());
    }

    @GetMapping("/list")
    public List<SysKv> list() {
        List<SysKv> list = sysKvService.list(null);
        return Optional.ofNullable(list).orElse(new ArrayList<>());
    }
    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }

}
