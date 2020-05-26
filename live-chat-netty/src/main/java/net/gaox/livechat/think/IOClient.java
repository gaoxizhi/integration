package net.gaox.livechat.think;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2020/5/23 23:29
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("local.gaox.site", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((LocalDateTime.now().toString() + ": hello from client!").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
