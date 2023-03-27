package net.gaox.jpa.config;

import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * <p> HibernateConfig </p>
 *
 * @author gaox·Eric
 * @date 2023-03-27 02:58
 */
@Component
public class HibernateConfig implements HibernatePropertiesCustomizer {
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.use_sql_comments", true);
        hibernateProperties.put("hibernate.integrator_provider",
                (IntegratorProvider) () -> Collections.singletonList(CommentIntegrator.INSTANCE));
    }
}