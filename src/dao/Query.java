package dao;

import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Static class for running a query and storing the result
 */
public class Query {
    private static ResultSet result;

    /**
     * Creates a select prepared statement and stores the result
     * @param query The provided query to run
     * @param numParams The number of query parameters
     * @param params The query parameters
     */
    public static void makeSelectQuery(String query, int numParams, String... params) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(query);
            // Loop through params and add each to the correct place in the prepared statement
            for (int i = 0; i < numParams; i++) {
                // i + 1 because the prepared statement parameters start at 1
                statement.setString(i + 1, params[i]);
            }

            result = statement.executeQuery();
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

    /**
     * Creates an insert prepared statement and stores the returned index
     * @param query The query to run
     * @param numParams The number of query parameters
     * @param params The query parameters
     */
    public static void makeInsertQuery(String query, int numParams, String... params) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // Loop through params and add each to the correct place in the prepared statement
            for (int i = 0; i < numParams; i++) {
                // i + 1 because the prepared statement parameters start at 1
                ps.setString(i+ 1, params[i]);
            }

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }
    }

    /**
     * Creates a delete prepared statement and executes
     * @param query The provided query to run
     * @param customer The customer to delete
     */
    public static void makeDeleteQuery(String query, Customer customer) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, customer.getId());

            ps.execute();
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }
    }

    /**
     * Created an update prepared statement and stores the returned index
     * @param query The provided query to run
     * @param numParams The number of query parameters
     * @param params The query parameters
     */
    public static void makeUpdateQuery(String query, int numParams, String... params) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // Loop through params and add each to the correct place in the prepared statement
            for (int i = 0; i < numParams; i++) {
                // i + 1 because the prepared statement parameters start at 1
                ps.setString(i + 1, params[i]);
            }

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }
    }
}
