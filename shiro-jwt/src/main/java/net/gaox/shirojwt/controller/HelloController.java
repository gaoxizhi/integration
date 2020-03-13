package net.gaox.shirojwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> sey hello </p>
 *
 * @author gaox·Eric
 * @date 2020/3/13 21:56
 */
@RestController
@Slf4j
public class HelloController {
    @GetMapping
    public String hello() {
        log.debug("sey hello!");
        return "Hello from <a href = \"http://gaox.net!\">gaox.net</a>";
    }
}