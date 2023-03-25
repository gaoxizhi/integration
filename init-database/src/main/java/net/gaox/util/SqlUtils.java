package net.gaox.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p> sql 执行工具类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 16:25
 */
@Slf4j
public class SqlUtils {

    /**
     * 执行 文件
     *
     * @param conn     conn
     * @param fileName 文件名
     * @throws IOException e
     */
    public static void run(Connection conn, String fileName) throws IOException {
        log.info("execute file: {}", fileName);
        ScriptRunner runner = new ScriptRunner(conn);
        String fullPath = SqlUtils.class.getClassLoader().getResource(fileName).getPath();
        FileReader reader;
        try {
            reader = new FileReader(fullPath);
        } catch (Exception e) {
            log.warn("file read has exception: ", e);
            throw new IOException(e);
        }
        runner.runScript(reader);
    }

    /**
     * 执行 sql
     *
     * @param conn conn
     * @param sql  sql
     * @throws SQLException e
     */
    public static void executeSql(Connection conn, String sql) throws SQLException {
        log.info("execute sql:\n{}", sql);
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }

    /**
     * 执行 sql 并获取 ResultSet
     *
     * @param conn conn
     * @param sql  sql
     * @throws SQLException e
     */
    public static ResultSet executeQuery(Connection conn, String sql) throws SQLException {
        log.info("executeQuery sql:\n{}", sql);
        Statement stat = conn.createStatement();
        return stat.executeQuery(sql);
    }

}
