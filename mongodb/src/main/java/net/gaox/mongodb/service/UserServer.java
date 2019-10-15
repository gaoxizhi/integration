package net.gaox.mongodb.service;

import com.mongodb.client.result.UpdateResult;
import net.gaox.mongodb.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gaox·Eric
 * @date 2019/1/21 22:55
 */
@Component
public class UserServer {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     *
     * @return
     */
    public User findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        User user = mongoTemplate.findOne(query, User.class);
        return user;
    }

    /**
     * 根据用户名查询对象
     *
     * @return
     */
    public User findById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        User user = mongoTemplate.findOne(query, User.class);
        return user;
    }

    /**
     * 查找所有的用户列表
     *
     * @return
     */
    public List<User> findAll() {
        final List<User> all = mongoTemplate.findAll(User.class);
        return all;
    }

    /**
     * 更新对象
     */
    public User update(User user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("age", user.getAge()).set("name", user.getName()).set("grade", user.getGrade());
        //更新查询返回结果集的第一条
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        //更新查询返回结果集的所有
        mongoTemplate.updateMulti(query, update, User.class);
        return this.findById(user.getId());
    }

    /**
     * 删除对象
     *
     * @param id
     */
    public Boolean deleteById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        //查找是否有该id
        if (null == this.findById(id)) {
            return null;
        }
        //删除此值
        mongoTemplate.remove(query, User.class);
        //查找是否还有该id
        if (null == this.findById(id)) {
            return true;
        }
        return false;
    }
}