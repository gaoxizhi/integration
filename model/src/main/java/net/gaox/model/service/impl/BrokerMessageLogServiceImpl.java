package net.gaox.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.gaox.model.entity.message.BrokerMessageLog;
import net.gaox.model.mapper.BrokerMessageLogMapper;
import net.gaox.model.service.BrokerMessageLogService;
import org.springframework.stereotype.Service;

/**
 * <p> 订单处理记录 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-01
 */
@Service
public class BrokerMessageLogServiceImpl extends ServiceImpl<BrokerMessageLogMapper, BrokerMessageLog>
        implements BrokerMessageLogService {

}
