package com.self.data;

import java.sql.*;

/**
 * @author GTsung
 * @date 2021/10/13
 */
public class TestDB {

    public static void main(String[] args) throws SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String pwd = "root";
        Connection connection = DriverManager.getConnection(url, username, pwd);
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("select * from user_info");
        while (set.next()) {
            System.out.println(set.getString(1));
            System.out.println(set.getString(2));
        }
        statement.close();
        connection.close();

        // 事务
        Connection connection1 = DriverManager.getConnection(url, username, pwd);
        // 手动事务
        connection1.setAutoCommit(false);
        Statement statement1 = connection1.createStatement();
        statement1.executeUpdate("update user_info set name='ss' where id=1");
        connection1.commit();
//        connection1.rollback();
    }
}
