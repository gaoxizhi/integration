package net.gaox.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.gaox.common.Constants;
import net.gaox.domain.config.DataBaseProperty;
import net.gaox.domain.dto.InitializeDTO;
import net.gaox.service.InitializeService;
import net.gaox.util.InitializeUtils;
import net.gaox.util.SqlUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;

/**
 * <p> 初始化配置 Service 实现 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 17:20
 */
@Slf4j
@Service
public class InitializeServiceImpl implements InitializeService {

    @Override
    public Boolean configurer(InitializeDTO initializeDto) {

        Connection connection = InitializeUtils.getConnection(initializeDto);

        try {
            SqlUtils.run(connection, Constants.INITIAL_SCHEMA_FILE);

            DataBaseProperty dataBaseProperty = DataBaseProperty.getByInitializeDTO(initializeDto);
            InitializeUtils.writeProperties(dataBaseProperty);
        } catch (Exception e) {
            log.info("数据库配置失败！", e);
            return false;
        }
        log.info("数据库配置成功！");
        return true;
    }
}
