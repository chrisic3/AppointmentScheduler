package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Static class for running a query and storing the result
 */
public class Query {
    private static ResultSet result;

    /**
     * Takes a query string, creates a statement, and stores the result
     * @param query The provided query to run
     */
    public static void makeQuery(String query) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }
    }

    /**
     * Returns the result
     * @return The result of the query
     */
    public static ResultSet getResult() {
        return result;
    }
}
