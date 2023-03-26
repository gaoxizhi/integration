package net.gaox.reflect.dynamic.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 金星 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-23 00:33
 */
@Slf4j
public class Venus implements Planet {

    @Override
    public String getBelief() {
        return "The only planet in the solar system without a magnetic field.";
    }

    @Override
    public void look(String place) {
        log.info("在 {} 观测", place);
    }

}