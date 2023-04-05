package net.gaox.controller;

import lombok.extern.slf4j.Slf4j;
import net.gaox.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p> Controller </p>
 *
 * @author gaox·Eric
 * @date 2023-04-05 20:38
 */
@Slf4j
@RestController
@RequestMapping("/")
public class WebController {

    @GetMapping("/get")
    public String get(@RequestParam("name") String name) {
        log.info("get method param name is : {}.", name);
        return "name: " + name;
    }

    @PostMapping("/post")
    public User post(HttpServletRequest request, @RequestBody User user) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        user.setSpCode(sessionId);
        log.info("post method param name is : {}.", user);
        return user;
    }

    @PostMapping("/file")
    public String file(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        log.info("file info name : {}, size = {}.", file.getName(), file.getSize());
        return originalFilename;
    }
}
