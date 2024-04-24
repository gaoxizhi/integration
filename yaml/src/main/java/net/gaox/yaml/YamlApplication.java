package net.gaox.yaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/9/4 16:04
 */
@SpringBootApplication(scanBasePackages = {"net.gaox.yaml"})
public class YamlApplication {

    public static void main(String[] args) {
        SpringApplication.run(YamlApplication.class, args);
    }
}