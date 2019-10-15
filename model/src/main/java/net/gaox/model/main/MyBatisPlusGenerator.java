package net.gaox.model.main;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p> mybatisPlus自动生成插件 </p>
 *
 * @author gaox·Eric
 * @date 2019/4/7 20:30
 */
public class MyBatisPlusGenerator {

    /**
     * 生成数据库
     */
    private final static String DATA_BASE = "erp";
    /**
     * 需要生成的表
     */
    private final static String[] TABLES =
            {"access_log", "item", "item_detail", "operation_log", "order_detail", "order",
                    "r_role_permission", "r_user_role", "sys_classify", "sys_kv",
                    "sys_permission", "sys_role", "sys_user"
            };
    /**
     * 生成配置文件目录
     */
    private final static String OUT_DIR = "C:\\data\\codeBase\\erp-api\\src\\main\\java";
    /**
     * 项目父级包路径
     */
    private final static String PARENT_PACKAGE = "net.gaox.erp";

    /**
     * 生成入口
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {

        // 1. 全局配置

        GlobalConfig config = new GlobalConfig()
                // 是否支持AR模式
                .setActiveRecord(true)
                // 作者
                .setAuthor("gaox·Eric")
                // 生成路径
                .setOutputDir(OUT_DIR)
                //配置swagger
                .setSwagger2(true)
                // 文件覆盖
                .setFileOverride(false)
                // 主键策略
                .setIdType(IdType.AUTO)
                // 设置生成的service接口的名字为:"表名的首字母+Service"
                .setServiceName("%sService")
                // IEmployeeService
                // 生成基本的resultMap
                .setBaseResultMap(true)
                // 生成基本的SQL片段
                .setBaseColumnList(true);

        // 2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig()
                // 设置数据库类型
                .setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/" + DATA_BASE + "?useUnicode=true&characterEncoding=utf-8&tinyInt1isBit=false&serverTimezone=Asia/Shanghai")
                .setUsername("root")
                .setPassword("root");

        // 3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig()
                // 全局大写命名
                .setCapitalMode(true)
                // 数据库表映射到实体的命名策略 --> 驼峰法
                .setNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setEntityBuilderModel(true)
                .entityTableFieldAnnotationEnable(true)
                // 生成的表
                .setInclude(TABLES);

        // 4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig()
                .setParent(PARENT_PACKAGE)
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("entity")
                .setXml("mapper");

        // 5. 整合配置
        AutoGenerator ag = new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        // 6. 执行
        ag.execute();
    }

}