package net.gaox.redis.mapper;

import net.gaox.redis.entity.Home;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 家庭表 Mapper 接口
 * </p>
 *
 * @author gaox
 * @since 2019-04-07
 */
@Mapper
public interface HomeMapper {
    /**
     * 添加一个家庭
     *
     * @param home
     * @return
     */

    @Insert("INSERT INTO home (create_user_id,alias) VALUES(#{create_user_id},#{alias})")
    Integer instHome(Home home);

    /**
     * @param id
     * @return
     */
    @Delete("DELETE FROM home WHERE id =#{id}")
    int deleteByid(Long id);

    /**
     * @param home
     * @return
     */
    @Update("UPDATE home SET alias = #{home.alias} WHERE id = #{home.id}")
    int updates(@Param("home") Home home);

    /**
     * @param id
     * @return
     */
    @Select("SELECT * FROM home WHERE id = #{id}")
    Home getHome(Long id);

    /**
     * 查询列表
     *
     * @return
     */
    @Select("SELECT * FROM home")
    List<Home> listHome();
}
