package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Static class to set up the db connection
 */
public class DBConnection {
    // Pieces that comprise the db connection string
    private static final String protocol = "jdbc";
    private static final String vendorName = "mysql";
    private static final String address = "localhost";
    private static final String port = "3306";
    private static final String databaseName = "client_schedule";
    private static final String url = protocol + ":" + vendorName + "://" + address + ":" + port + "/" + databaseName;

    // Db username and password
    // Probably need to do something else with the password...
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";

    // Used to store the open connection
    private static Connection conn;

    /**
     * Attempts to open a connection
     */
    public static void openConnection() {
        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the stored open connection
     * @return The stored open connection
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Closes an open connection
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
