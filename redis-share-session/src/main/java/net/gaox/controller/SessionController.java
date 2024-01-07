package net.gaox.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p> session 测试接口 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 20:58
 */

@RestController
@RequestMapping
public class SessionController {

    /**
     * 项目端口
     */
    @Value("${server.port}")
    private Integer projectPort;

    @RequestMapping(value = "/createSession", method = {RequestMethod.GET, RequestMethod.POST})
    public String createSession(HttpSession session, String name) {
        session.setAttribute("name", name);
        return "当前项目端口：" + projectPort + " 当前sessionId :" + session.getId() + "在Session中存入成功！";
    }

    @GetMapping("/getSession")
    public String getSession(HttpSession session) {
        String name = String.valueOf(session.getAttribute("name"));
        String sessionId = session.getId();
        return "当前项目端口：" + projectPort + " 当前sessionId :" + sessionId + "  获取的姓名:" + name;
    }

}
