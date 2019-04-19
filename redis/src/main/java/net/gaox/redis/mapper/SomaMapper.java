package net.gaox.redis.mapper;

import net.gaox.redis.entity.Home;
import net.gaox.redis.entity.SomaTempLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName SomaMapper
 * @Author: gaox·Eric
 * @Date: 2019/4/14 10:51
 */
@Mapper
public interface SomaMapper {
    /**
     * 通过id查询信息
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM `soma_temp_log` WHERE id = #{id}")
    SomaTempLog getSomaByid(Long id);

    /**
     * 查询历史信息列表
     *
     * @return
     */
    @Select("SELECT * FROM `soma_temp_log`")
    List<Home> somaList();
}
