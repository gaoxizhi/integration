package net.gaox.base.service;


import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.entity.SysKv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-04-11 19:12
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysKvServiceTest {

    @Resource
    private SysKvService sysKvService;

    @Test
    public void list() {
        List<SysKv> list = sysKvService.list(null);
        log.info("list:{}", list);
    }

}
