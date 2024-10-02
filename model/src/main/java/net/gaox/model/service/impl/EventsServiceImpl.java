package net.gaox.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.gaox.model.entity.Events;
import net.gaox.model.mapper.EventsMapper;
import net.gaox.model.service.EventsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>  服务实现类 </p>
 *
 * @author gaox·Eric
 * @since 2024-09-11
 */
@Service
public class EventsServiceImpl extends ServiceImpl<EventsMapper, Events> implements EventsService {

    @Override
    public IPage<Events> findEventsByParticipant(List<String> names, Integer pageNo, Integer pageSize) {
        String[] array = names.toArray(new String[0]);
        LambdaQueryWrapper<Events> queryWrapper = new LambdaQueryWrapper<Events>();
        // 遍历每个 participantName，动态构建 OR 条件
        // for (String name : names) {
        //     queryWrapper.or(wrapper -> wrapper.apply("JSON_CONTAINS(participants, JSON_QUOTE({0}), '$')", name));
        // }
        // 使用 or 嵌套，确保所有 JSON_CONTAINS 条件组合在一起
        queryWrapper.and(wrapper -> {
            // 遍历每个 participantName，动态构建 OR 条件
            for (String name : names) {
                wrapper.or().apply("JSON_CONTAINS(participants, JSON_QUOTE({0}), '$')", name);
            }
            return wrapper;
        });
        IPage<Events> page = page(new Page<>(pageNo, pageSize), queryWrapper);
        return page;
    }
}
