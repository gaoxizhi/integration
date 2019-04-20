package net.gaox.mongodb.config;

import net.gaox.mongodb.pojo.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description: <p> MongoDB自定义存储结构 </p>
 * @Author: gaox·Eric
 * @Date: 2019/1/21 23:40
 */
@Configuration
public interface UserRepository extends MongoRepository<User, String> {
}
