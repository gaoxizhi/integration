package net.gaox.redis.controller;

import net.gaox.redis.entity.SomaTempLog;
import net.gaox.redis.service.SomaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gaox·Eric
 * @date 2019/4/13 14:35
 */
@RestController
public class SomaController {
    final
    SomaService somaService;

    public SomaController(SomaService somaService) {
        this.somaService = somaService;
    }

    @GetMapping("/soma/{id}")
    public SomaTempLog getSoma(@PathVariable String id) {
        System.out.println("接受查询请求，ID：" + id);
        return somaService.getSomaByid(Long.parseLong(id));
    }

    @GetMapping("/soma/list")
    public List<SomaTempLog> getList() {
        return somaService.somaList();
    }
}