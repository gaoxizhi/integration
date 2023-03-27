package net.gaox.restart;

import lombok.extern.slf4j.Slf4j;
import net.gaox.util.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p> 运行时重启 SpringBoot Application </p>
 *
 * @author gaox·Eric
 * @date 2023-03-26 20:19
 */

@Slf4j
@RestController
@SpringBootApplication
public class App {

    private final String applicationNameKey = "spring.application.name";
    private final String portKey = "server.port";

    @Value("${spring.application.name}")
    String appName;

    private static String[] args;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        App.args = args;
        App.context = SpringApplication.run(App.class, args);
    }

    @GetMapping("/refresh")
    public String restart(Integer port, String applicationName) {
        log.info(applicationNameKey + ":" + appName);
        try {
            Properties properties = PropertiesUtil.get();
            properties.setProperty(applicationNameKey, applicationName);
            properties.setProperty(portKey, String.valueOf(port));
            PropertiesUtil.saveProperties(properties, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ExecutorService threadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        threadPool.execute(() -> {
            context.close();
            context = SpringApplication.run(App.class, args);
        });

        threadPool.shutdown();
        return "spring.application.name:" + appName;
    }

    @GetMapping("/info")
    public String info() {
        log.info("spring.application.name:" + appName);
        return appName;
    }

}
