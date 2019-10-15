package net.gaox.model.main;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Description: <p> mybaits-plus代码生成器 </p>
 * @ClassName: MpGenerator
 * @author gaox·Eric
 * @date 2019/7/13 12:09
 */
public class MpGenerator {

    public static void main(String[] args) throws InterruptedException {

        //用来获取Mybatis-Plus.properties文件的配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //生成文件的输出目录【默认 D 盘根目录】
        gc.setOutputDir(rb.getString("OutputDir"))
                //开启 swagger2 模式
                .setSwagger2(true)
                //是否打开输出目录
                .setOpen(false)
                //开启 BaseResultMap
                .setBaseResultMap(true)
                //开启 baseColumnList
                .setBaseColumnList(true)
                //设置开发人员
                .setAuthor(rb.getString("author"))
                //覆盖生成
                .setFileOverride(true)
                //设置生成的各类文件名
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        //设置数据数据库类型
        dsc.setDbType(DbType.MYSQL)
                //设置数据路连接URL
                .setUrl(rb.getString("url"))
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername(rb.getString("userName"))
                .setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        // 包配置

        //装代码的文件夹名
        String className = rb.getString("className");
        className = "".equals(className) ? "" : "." + className;
        PackageConfig pc = new PackageConfig();
        pc.setParent(rb.getString("parent"))
                .setController("controller" + className)
                .setService("service" + className)
                .setServiceImpl("service" + className + ".impl")
                .setEntity("entity" + className)
                .setMapper("mapper" + className);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //装代码的文件夹名
                String className = rb.getString("className");
                className = "".equals(className) ? "" : className + "/";
                // 自定义输入文件名称
                return rb.getString("OutputDirXml") + "/mapper/" + className + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //实体类命名方式
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(rb.getString("tableName").split(","));
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }
}
