package net.gaox.jdbc;

import java.sql.*;

/**
 * @Description: <p>  </p>
 * @ClassName ConnectionMysql
 * @Author: gaox·Eric
 * @Date: 2019/3/31 17:28
 */
public class ConnectionMysql {

    public static Connection getConnection() {

        String driver = "com.mysql.jdbc.Driver";
        // String url = "jdbc:mysql://localhost:3306/demo";
        // mysql版本较高,报SSL警告
        String url = "jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "***youruser**";
        String password = "***yourpass**";

        Connection conn = null;

        try {
            // classLoader,加载对应驱动//(1)
            Class.forName(driver);
            // 进行链接(2)
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("conn");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // 这异常要经常处理!(3)
            e.printStackTrace();
        }
        // 返回Connection对象
        return conn;
    }


    private static Integer getAll() {
        Connection conn = getConnection();
        System.out.println(conn);
        String sql = "select * from t_question";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("+----------------------------+");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            System.out.println("+----------------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println("getall:");
        getAll();
    }
}
