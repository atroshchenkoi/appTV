package com.example.apptv.data_base_connection;

import java.sql.*;

public class DataBaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/weather";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
