package net.gaox.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.gaox.model.entity.Events;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>  Mapper 接口 </p>
 *
 * @author gaox·Eric
 * @since 2024-09-11
 */
public interface EventsMapper extends BaseMapper<Events> {

    @Select("SELECT * FROM `events` WHERE JSON_CONTAINS(participants, JSON_QUOTE(#{participantName}), '$')")
    List<Events> findEventsByParticipant(String participantName);

}
