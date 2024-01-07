package net.gaox.util;

import lombok.extern.slf4j.Slf4j;
import net.gaox.common.Constants;
import net.gaox.domain.config.DataBaseProperty;
import net.gaox.domain.dto.InitializeDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 初始化工具类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 16:29
 */
@Slf4j
public class InitializeUtils {

    public static Connection getConnection(InitializeDTO initializeDto) {
        Connection connection;
        String db = initializeDto.getDb();

        int retryTime = 3;

        for (int i = 0; i < retryTime; i++) {
            connection = createConnection(initializeDto);
            if (null != connection) {
                log.info("获取数据库连接成功！");
                return connection;
            } else {
                initializeDto.setDb(Constants.MYSQL_BASE_DATABASE);
                connection = createConnection(initializeDto);

                // 创建数据库 dbName
                try {
                    initializeDto.setDb(db);
                    DataBaseProperty property = DataBaseProperty.getByInitializeDTO(initializeDto);
                    createAndUseDatabaseIfAbsent(connection, property);
                } catch (SQLException e) {
                    log.warn("创建数据库 {} 失败", db);
                }
            }
        }
        throw new RuntimeException("获取数据库连接失败");
    }

    /**
     * 从 InitializeDTO 创建 数据库连接
     *
     * @param initializeDto dto
     * @return 数据库连接
     */
    public static Connection createConnection(InitializeDTO initializeDto) {
        Connection connection = null;
        try {
            DataBaseProperty property = DataBaseProperty.getByInitializeDTO(initializeDto);
            Class.forName(property.getDriverClassName()).newInstance();
            connection = DriverManager.getConnection(property.getConnectionUrl(), property.getUsername(), property.getPassword());
        } catch (Exception e) {
            log.warn("尝试获取 connection 失败");
        }
        return connection;
    }

    /**
     * 创建并且使用 database
     *
     * @param connection con
     * @param property   配置文件
     * @throws SQLException e
     */
    public static void createAndUseDatabaseIfAbsent(Connection connection, DataBaseProperty property) throws SQLException {
        String createDB = "CREATE DATABASE IF NOT EXISTS `" + property.getDatabase()
                + "` DEFAULT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';";
        SqlUtils.executeSql(connection, createDB);

        String useDB = "USE `" + property.getDatabase() + "` ;";
        SqlUtils.executeSql(connection, useDB);
    }

    /**
     * 写 数据库配置文件
     *
     * @param property 连接信息ø
     */
    public static void writeProperties(DataBaseProperty property) {
        Map<String, String> map = new HashMap<>(4);
        map.put(Constants.MYSQL_URL_KEY, property.getConnectionUrl());
        map.put(Constants.MYSQL_USERNAME_KEY, property.getUsername());
        map.put(Constants.MYSQL_PASSWORD_KEY, property.getPassword());
        FileUtils.setPropertyFile(Constants.MYSQL_CONFIG_FILE_NAME, map);
    }

}
