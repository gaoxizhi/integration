package net.gaox.page.interceptor;


import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: <p>  </p>
 * @ClassName: GaoPageInterceptor
 * @author gaox·Eric
 * @date 2019/7/14 22:12
 * @Intercepts 说明是一个拦截器
 * @Signature 拦截器的签名
 * type 拦截的类型 四大对象之一( Executor,ResultSetHandler,ParameterHandler,StatementHandler)
 * method 拦截的方法
 * args 参数
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
        args = {Connection.class, Integer.class})})
public class GaoPageInterceptor implements Interceptor {

    /**
     * 每页显示的条目数
     */
    private int size;
    /**
     * 当前现实的页数
     */
    private int page;
    private String dbType;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取StatementHandler，默认是RoutingStatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //获取statementHandler包装类
        MetaObject MetaObjectHandler = SystemMetaObject.forObject(statementHandler);

        //分离代理对象链
        while (MetaObjectHandler.hasGetter("h")) {
            Object obj = MetaObjectHandler.getValue("h");
            MetaObjectHandler = SystemMetaObject.forObject(obj);
        }

        while (MetaObjectHandler.hasGetter("target")) {
            Object obj = MetaObjectHandler.getValue("target");
            MetaObjectHandler = SystemMetaObject.forObject(obj);
        }

        //获取连接对象
        //Connection connection = (Connection) invocation.getArgs()[0];
        //获取StatementHandler的实现类
        //object.getValue("delegate");

        //获取查询接口映射的相关信息
        MappedStatement mappedStatement =
                (MappedStatement) MetaObjectHandler.getValue("delegate.mappedStatement");
        String mapId = mappedStatement.getId();
        //statementHandler.getBoundSql().getParameterObject();
        System.out.println("hasn't get GaoPage");

        //拦截以.GaoPage结尾的请求，分页功能的统一实现
        if (mapId.matches(".+GaoPage$")) {
            System.out.println("get GaoPage");
            //获取进行数据库操作时管理参数的handler
            ParameterHandler parameterHandler =
                    (ParameterHandler) MetaObjectHandler.getValue("delegate.parameterHandler");
            //获取请求时的参数
            Map<String, Object> paraObject =
                    (Map<String, Object>) parameterHandler.getParameterObject();

            //也可以这样获取
            //paraObject = (Map<String, Object>) statementHandler.getBoundSql().getParameterObject();
            //参数名称和在service中设置到map中的名称一致
            page = (int) paraObject.get("page");
            size = (int) paraObject.get("size");
            String sql = (String) MetaObjectHandler.getValue("delegate.boundSql.sql");

            //也可以通过statementHandler直接获取
            //sql = statementHandler.getBoundSql().getSql();

            //构建分页功能的sql语句
            String limitSql;
            sql = sql.trim();
            limitSql = sql + " limit " + (page - 1) * size + "," + size;
            //将构建完成的分页sql语句赋值个体'delegate.boundSql.sql'，偷天换日
            MetaObjectHandler.setValue("delegate.boundSql.sql", limitSql);
        }

        //调用原对象的方法，进入责任链的下一级
        return invocation.proceed();
    }

    /**
     * 获取代理对象
     */
    @Override
    public Object plugin(Object o) {
        //生成object对象的动态代理对象
        return Plugin.wrap(o, this);
    }

    /**
     * 设置代理对象的参数
     */
    @Override
    public void setProperties(Properties properties) {
        //如果项目中分页的size是统一的，也可以在这里统一配置和获取，
        //这样就不用每次请求都传递size参数了。参数是在配置拦截器时配置的。
        String limit = properties.getProperty("limit", "10");
        this.size = Integer.valueOf(limit);
        this.dbType = properties.getProperty("dbType", "mysql");
    }
}
