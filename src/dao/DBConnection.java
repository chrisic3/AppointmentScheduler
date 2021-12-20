package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String databaseName = "client_schedule";
    private static final String port = "3306";
    private static final String url = "jdbc:mysql://localhost:" + port + "/" + databaseName;
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    private static Connection conn;

    public static void openConnection() {
        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
