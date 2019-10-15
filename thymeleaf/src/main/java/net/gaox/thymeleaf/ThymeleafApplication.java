package net.gaox.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p> 启动类 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@SpringBootApplication
public class ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafApplication.class, args);
    }

//    @Bean
//    public ViewResolver myViewReolver(){
//        return new MyViewResolver();
//    }
//
//    public static class MyViewResolver implements ViewResolver{
//
//        @Override
//        public View resolveViewName(String viewName, Locale locale) throws Exception {
//            return null;
//        }
//    }
}

