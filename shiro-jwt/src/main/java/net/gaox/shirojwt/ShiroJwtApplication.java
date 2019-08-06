package net.gaox.shirojwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 */
@EnableScheduling
@SpringBootApplication
public class ShiroJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtApplication.class, args);
    }
}