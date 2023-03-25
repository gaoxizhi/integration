package net.gaox.controller;

import lombok.AllArgsConstructor;
import net.gaox.domain.dto.InitializeDTO;
import net.gaox.service.InitializeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p> 数据库创建 Controller </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 17:15
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class InitController {

    private final InitializeService initializeService;

    /**
     * 初始化配置
     *
     * @param initialize 配置信息
     * @return 配置状态
     */
    @PostMapping("/init")
    public Boolean init(@RequestBody InitializeDTO initialize) {
        return initializeService.configurer(initialize);
    }
}

