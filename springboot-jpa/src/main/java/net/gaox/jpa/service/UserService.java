package net.gaox.jpa.service;

import net.gaox.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p> 服务类接口 </p>
  UserService
 * @author gaox·Eric
 * @date 2019/5/2 15:31
 */
@Repository
public interface UserService extends JpaRepository<User, Long> {

    /**
     * 查询名称等于某两个名字的所有用户列表
     * 这里是用 PQL 的语法来定义一个查询。其中两个参数名字有语句中的 : 后面的值来决定
     * @param name1
     * @param name2
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.name = :name1  OR u.name = :name2 ")
    List<User> findTwoName(@Param("name1") String name1, @Param("name2") String name2);

    /**
     * 同上，使用SQL语句
     * nativeQuery = true
     * 采用原生 SQL 语句的方式来编写查询
     * @param name1
     * @param name2
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE name = :name1  OR name = :name2 ")
    List<User> findSQL(@Param("name1") String name1, @Param("name2") String name2);
}