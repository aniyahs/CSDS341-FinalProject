package main.java.com.finalProj.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:sqlserver://cxp-sql-03;databaseName=OnlineOrders;user=lmd117;password=VCyBedcpKohw87";
    private static final String USERNAME = "lmd117";
    private static final String PASSWORD = "VCyBedcpKohw87";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("SQL Server JDBC Driver not found.");
        }

        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

