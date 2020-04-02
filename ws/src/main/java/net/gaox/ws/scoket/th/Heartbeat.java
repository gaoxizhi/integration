package net.gaox.ws.scoket.th;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.util.date.LdtTimeUnit;

import javax.websocket.Session;
import java.time.LocalDateTime;

/**
 * <p> socket 心跳包 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/1 00:18
 */
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Heartbeat implements Runnable {
    private Session session;
    private String sid;

    @Override
    public void run() {
        LocalDateTime last = LocalDateTime.now();
        log.info("服务端开启发送心跳模式");
        while (session.isOpen()) {
            try {
                LocalDateTime now = LocalDateTime.now();
                String uuid = String.format("客户端[%s], %s连接到服务器", sid, LdtTimeUnit.durationTime(last, now));
                log.info(uuid);
                //并发情况下使用
                session.getAsyncRemote().sendText(uuid);
                Thread.sleep(20_000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}