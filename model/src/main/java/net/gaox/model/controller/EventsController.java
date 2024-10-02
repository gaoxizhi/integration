package net.gaox.model.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.gaox.model.entity.Events;
import net.gaox.model.mapper.EventsMapper;
import net.gaox.model.service.EventsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>  前端控制器 </p>
 *
 * @author gaox·Eric
 * @since 2024-09-11
 */
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventsMapper eventsMapper;
    private final EventsService eventsService;

    @GetMapping
    public List<Events> get(String participants) {
        List<Events> events = eventsMapper.findEventsByParticipant(participants);

        events = Optional.ofNullable(events).orElse(new ArrayList<>()).stream()
                .peek(s -> s.setParticipantList(JSON.parseArray(s.getParticipants(), String.class)))
                .collect(Collectors.toList());
        return events;
    }

    @GetMapping("/list")
    public IPage<Events> get(@RequestBody EventsQuery query) {

        IPage<Events> page = eventsService.findEventsByParticipant(query.getParticipants(),
                query.getPageNo(), query.getPageSize());

        List<Events> list = Optional.ofNullable(page.getRecords()).orElse(new ArrayList<>()).stream()
                .peek(s -> s.setParticipantList(JSON.parseArray(s.getParticipants(), String.class)))
                .collect(Collectors.toList());
        page.setRecords(list);
        return page;
    }

    @Data
    class EventsQuery {
        private List<String> participants;
        private Integer pageNo = 1;
        private Integer pageSize = 10;
    }

}
