package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Static class used to get a user from the db
 */
public class UserDAO {
    public static ObservableList<User> getUsers() {
        String query = "SELECT User_ID, User_Name, User_Password FROM users";
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");

                users.add(new User(id, username, password));
            }
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Runs a query to get the user with the corresponding username and password and Creates a User
     * object based on the results
     * @param usernameInput The username to match in the db
     * @param passwordInput The password to match in the db
     * @return Returns a user object if found, otherwise returns null
     */
    public static User getUser(String usernameInput, String passwordInput) {
        String query = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";

        try {
            Query.makeSelectQuery(query, 2, usernameInput, passwordInput);
            ResultSet rs = Query.getResult();

            rs.next();

            int id = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");
            return new User(id, username, password);
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return null;
    }

    public static User getUser(int idInput) {
        String query = "SELECT User_ID, User_Name, Password FROM users WHERE User_ID = ?";

        try {
            Query.makeSelectQuery(query, 1, String.valueOf(idInput));
            ResultSet rs = Query.getResult();

            rs.next();

            int id = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");
            return new User(id, username, password);
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return null;
    }
}
