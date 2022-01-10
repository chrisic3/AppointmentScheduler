package dao;

import model.Appointment;
import model.Customer;
import model.Division;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
     * Creates a delete prepared statement and executes
     * @param query The provided query to run
     * @param id The id of the item to delete
     */
    public static void makeDeleteQuery(String query, int id) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);

            ps.execute();
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }
    }

    /**
     * Inserts a customer into the db
     * @param query The insert query to run
     * @param customer The customer to insert
     */
    public static void insertCustomer(String query, Customer customer) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setInt(3, customer.getDivision().getId());
            ps.setString(4, customer.getZip());
            ps.setString(5, customer.getPhone());

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a customer in the db
     * @param query The update query to run
     * @param customer The customer to update
     */
    public static void updateCustomer(String query, Customer customer) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setInt(3, customer.getDivision().getId());
            ps.setString(4, customer.getZip());
            ps.setString(5, customer.getPhone());
            ps.setInt(6, customer.getId());

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }
    }

    /**
     * Inserts an appointment into the db
     * @param query The insert query to run
     * @param appointment The appointment to insert
     */
    public static void insertAppointment(String query, Appointment appointment) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setInt(7, appointment.getCustomer().getId());
            ps.setInt(8, appointment.getUser().getId());
            ps.setInt(9, appointment.getContact().getId());

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an appointment in the db
     * @param query The update query to run
     * @param appointment The appointment to update
     */
    public static void updateAppointment(String query, Appointment appointment) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setInt(7, appointment.getCustomer().getId());
            ps.setInt(8, appointment.getUser().getId());
            ps.setInt(9, appointment.getContact().getId());
            ps.setInt(10, appointment.getId());

            ps.execute();

            result = ps.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * Creates an insert prepared statement and stores the returned index
//     * @param query The query to run
//     * @param numParams The number of query parameters
//     * @param params The query parameters
//     */
//    public static void makeInsertQuery(String query, int numParams, String... params) {
//        try {
//            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            // Loop through params and add each to the correct place in the prepared statement
//            for (int i = 0; i < numParams; i++) {
//                // i + 1 because the prepared statement parameters start at 1
//                ps.setString(i+ 1, params[i]);
//            }
//
//            ps.execute();
//
//            result = ps.getGeneratedKeys();
//        } catch (SQLException e) {
//            // Db error
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Created an update prepared statement and stores the returned index
//     * @param query The provided query to run
//     * @param numParams The number of query parameters
//     * @param params The query parameters
//     */
//    public static void makeUpdateQuery(String query, int numParams, String... params) {
//        try {
//            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            // Loop through params and add each to the correct place in the prepared statement
//            for (int i = 0; i < numParams; i++) {
//                // i + 1 because the prepared statement parameters start at 1
//                ps.setString(i + 1, params[i]);
//            }
//
//            ps.execute();
//
//            result = ps.getGeneratedKeys();
//        } catch (SQLException e) {
//            // Db error
//            e.printStackTrace();
//        }
//    }
}
