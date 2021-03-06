package net.gaox.jpa.service;

import net.gaox.jpa.entity.User;
import net.gaox.jpa.entity.UserClothes;
import net.gaox.jpa.entity.UserClothesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p> 联合主键示例 </p>
 * UserClothesService
 *
 * @author gaox·Eric
 * @date 2019/5/2 23:42
 */
@Repository
public interface UserClothesService extends JpaRepository<UserClothes, UserClothesId> {
    /**
     * 使用hql语句，爬坑：
     * <p>
     * From后是类名，字段名按照类名属性来做
     * 总结应该按照类结构编写
     * </p>
     *
     * @param id
     * @return
     */
//    @Query("SELECT u FROM User u ,UserClothes uc WHERE u.id = uc.id.userId AND uc.id.clothesId = :id")
    @Query("SELECT u FROM User u ,UserClothes uc WHERE u.id = uc.userId AND uc.clothesId = :id")
    List<User> findUsersByClothes(@Param("id") Long id);

    /**
     * 查找通过用户id和衣服id
     *
     * @param userId
     * @param clothesId
     * @return
     */
    UserClothes findByUserIdAndClothesId(Long userId, Long clothesId);
}