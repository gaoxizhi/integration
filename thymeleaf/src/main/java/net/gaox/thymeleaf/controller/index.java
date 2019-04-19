package net.gaox.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Description: <p>  </p>
 * @Auther: gaox·Eric
 * @Date: 2019/1/25 11:03
 */
@Controller
public class index {
    @GetMapping(value = {"/","/index.html","/index"})
    public String index(Map<String,Object> map){
        map.put("msg",0);
        map.put("time", LocalDateTime.now());
        System.out.println(1111);
        return "index";
    }
}
