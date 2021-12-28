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
     * Takes a query string, creates a statement, and stores the result
     * @param query The provided query to run
     */
    public static void makeSelectQuery(String query, int numParams, String... params) {
        try {

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(query);
            for (int i = 0; i < numParams; i++) {
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

    public static void makeInsertQuery(String query, int numParams, String... params) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < numParams; i++) {
                ps.setString(i+ 1, params[i]);
            }

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void makeDeleteQuery(String query, Customer customer) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, customer.getId());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void makeUpdateQuery(String query, int numParams, String... params) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < numParams; i++) {
                ps.setString(i + 1, params[i]);
            }

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
