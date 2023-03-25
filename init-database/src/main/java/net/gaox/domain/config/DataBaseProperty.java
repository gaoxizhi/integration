package net.gaox.domain.config;

import lombok.Data;
import net.gaox.domain.dto.InitializeDTO;

/**
 * <p> 数据库配置 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 16:33
 */
@Data
public class DataBaseProperty {
    private String host = "localhost";
    private String database = "gaox";
    private String username = "root";
    private String password = "root";
    private String port = "3306";
    private String driverClassName = "com.mysql.jdbc.Driver";

    /**
     * 获取数据库连接地址
     * 缺省 url=jdbc:mysql://localhost:3306/gaox?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&useUnicode=true
     *
     * @return 数据库连接 url
     */
    public String getConnectionUrl() {
        StringBuilder url = new StringBuilder("jdbc:mysql://");
        url.append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(database)
                .append("?useUnicode=true&useSSL=false&characterEncoding=utf8");
        return url.toString();
    }

    /**
     * 从 InitializeDTO 获取
     *
     * @param data dto
     * @return DataBaseProperty
     */
    public static DataBaseProperty getByInitializeDTO(InitializeDTO data) {
        DataBaseProperty property = new DataBaseProperty();
        property.setPassword(data.getPassword());
        property.setUsername(data.getUsername());
        property.setPort(data.getPort());
        property.setHost(data.getHost());
        property.setDatabase(data.getDb());
        return property;
    }

}