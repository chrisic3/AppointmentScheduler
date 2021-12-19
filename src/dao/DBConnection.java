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
    public static Connection conn;

    public static void openConnection() throws SQLException {
        conn = (Connection) DriverManager.getConnection(url, username, password);
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }
}
