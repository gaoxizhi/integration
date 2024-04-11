# 在nacos中增加名为system的dataId

```properties
server.port=12602
server.servlet.context-path=/
mybatis-plus.typeAliasesPackage=net.gaox.domain.entity
mybatis-plus.mapper-locations=classpath:/mybatis/**/*Mapper.xml
mybatis-plus.configuration.map-underscore-to-camel-case=true
logging.level.net.gaox.base=debug
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/iot?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&useUnicode=true
spring.cloud.nacos.discovery.ephemeral=false
```
