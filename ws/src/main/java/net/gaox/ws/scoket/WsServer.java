package net.gaox.ws.scoket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.ws.config.GetHttpSessionConfigurator;
import net.gaox.ws.scoket.th.Heartbeat;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> WebSocket 实现 </p>
 * <p>
 * 功能列表
 * 1. 监听
 * 2. 单处登录
 * 3. 心跳发送
 * 4. 线程终止（中断）
 * 5. 群发
 * 6. http推送
 * </p>
 *
 * @author gaox·Eric
 * @date 2020/4/1 00:17
 */

@Slf4j
@Component
@ServerEndpoint(value = "/id/{id}", configurator = GetHttpSessionConfigurator.class)
public class WsServer {

    /**
     * 设定原子整型,用来记录当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * 心跳线程列表
     */
    private static ConcurrentHashMap<String, Thread> threads = new ConcurrentHashMap<>();
    /**
     * 绑定HttpSession与session
     */
    public static ConcurrentHashMap<String, Session> bizSession = new ConcurrentHashMap<>();
    /**
     * 若要实现客户端单处登录，可以使用Map来存放，其中Key可以为用户id，value为Session
     */
    public static ConcurrentHashMap<String, Session> onlyMap = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     *
     * @param session session对象
     * @param config  配置器
     * @param id      id 客户端标识
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("id") String id) {

        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        log.info("httpSessionId:[{}], websocketSessionId:[{}], id:[{}].", httpSession.getId(), session.getId(), id);

        //检查是否已登陆
        if (onlyMap.containsKey(id)) {
            //踢出已登录
            Session oldSession = onlyMap.get(id);

            JSONObject toMessage = new JSONObject();
            toMessage.put("kickOut", "该用户已在其他地方登录，请重新登录！");
            // 即时发送消息
            sendMessage(JSONObject.toJSON(toMessage).toString(), oldSession);
            try {
                oldSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //销毁心跳线程
//            Thread t = threads.get(id);
//            threads.remove(id);
//            t.interrupt();
        }
        //建立关联
        bizSession.put(httpSession.getId(), session);
        onlyMap.put(id, session);
        //在线数加1
        addOnlineCount();
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        sendMessage("[" + httpSession.getId() + "]连接成功", session);
        //设定心跳线程
        Thread thread = new Thread(new Heartbeat(session, id));
        thread.setName(httpSession.getId());
        thread.start();
        threads.put(id, thread);
    }

    /**
     * 连接关闭时调用的方法
     *
     * @param closeSession session对象
     * @param reason       连接关闭原因
     */
    @OnClose
    public void onClose(Session closeSession, CloseReason reason) {
        log.info(reason.toString());
        for (String key : bizSession.keySet()) {
            Session session = bizSession.get(key);
            if (session.equals(closeSession)) {
                bizSession.remove(key);
            }
        }
        //在线数减1
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message   客户端发送过来的消息
     * @param mySession session对象
     */
    @OnMessage
    public void onMessage(String message, Session mySession) {
        log.info("来自客户端的消息:" + message);
        if (message.startsWith("qun: ")) {
            sendAll(message.substring(5), mySession);
        } else {
            sendMessage(message, mySession);
        }
    }

    /**
     * @param session session客户端标识
     * @param error   异常
     */

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        for (String key : bizSession.keySet()) {
            Session errSession = bizSession.get(key);
            if (session.equals(errSession)) {
                bizSession.remove(key);
            }
        }
        error.printStackTrace();
    }

    /**
     * 像指定session发送消息
     *
     * @param message 消息内容
     * @param session session
     */
    public synchronized static void sendMessage(String message, Session session) {
        try {
            if (session.isOpen()) {
                //该session如果已被删除，则不执行发送请求，防止报错
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     * @param session 发送者session
     */
    public synchronized static void sendAll(String message, Session session) {
        onlyMap.forEach((k, v) -> {
            if (!session.getId().equals(v.getId())) {
                sendMessage(message, v);
            }
        });
    }

    /**
     * 自定义消息推送
     */
    public static void pushMessage(String message, @PathParam("id") String id) {
        log.info("发送消息到:" + id + "，报文:" + message);
        if (StringUtils.isEmpty(id) && onlyMap.containsKey(id)) {
            sendMessage(message, onlyMap.get(id));
        } else {
            log.error("用户" + id + ",不在线！");
        }
    }

    /**
     * 获取当前在线人数
     *
     * @return 当前在线人数
     */
    public int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 在线人数+1
     *
     * @return 当前在线人数
     */
    public int addOnlineCount() {
        return onlineCount.getAndIncrement();
    }

    /**
     * 在线人数-1
     *
     * @return 当前在线人数
     */
    public int subOnlineCount() {
        return onlineCount.getAndDecrement();
    }
}