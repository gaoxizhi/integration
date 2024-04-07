package net.gaox.relation.mapper;


import net.gaox.relation.config.mybatis.EnumCodeTypeHandler;
import net.gaox.relation.model.dto.ItemDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

/**
 * <p> 商品表 mapper 接口 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-10
 */
public interface ItemMapper {

    @Select("SELECT * FROM `item` WHERE `id` = #{id}")
    @Result(column = "del_flag", property = "delFlag", typeHandler = EnumCodeTypeHandler.class)
    ItemDTO findItemById(@Param("id") Long id);

}