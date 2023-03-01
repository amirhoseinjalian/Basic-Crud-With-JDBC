package com.jalian.hw7.projectUtillity;
import java.sql.Connection;
import java.sql.DriverManager;
public class MySqlConnection {
    private static Connection connection;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw7", "root", "1382");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static Connection getConnection() {
        return connection;
    }
}