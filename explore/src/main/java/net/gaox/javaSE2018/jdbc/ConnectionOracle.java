package net.gaox.javaSE2018.jdbc;

import java.sql.*;

/**
 * @Description: <p>  </p>
 * @ClassName ConnectionOracle
 * @Author: gaox·Eric
 * @Date: 2019/3/31 18:04
 */
public class ConnectionOracle {

    static String driver = "oracle.jdbc.driver.OracleDriver";
    // String url = "jdbc:mysql://localhost:3306/demo";
    // mysql版本较高,报SSL警告
    static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    static String username = "scott";
    static String password = "123456";

    static Connection conn = null;

    static PreparedStatement pstmt;

    public static Connection getConnection() {
        try {
            // classLoader,加载对应驱动//(1)
            Class.forName(driver);
            // 进行链接(2)
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // 这异常要经常处理!(3)
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        String sql = "select ename from emp";
        ResultSet rs = null;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("+----------------------------+");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println("");
            }
            System.out.println("+----------------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
