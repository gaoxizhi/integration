package net.gaox.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.gaox.model.entity.Events;

import java.util.List;

/**
 * <p>  服务类 </p>
 *
 * @author gaox·Eric
 * @since 2024-09-11
 */
public interface EventsService extends IService<Events> {

    IPage<Events> findEventsByParticipant(List<String> names, Integer pageNo, Integer pageSize);

}
