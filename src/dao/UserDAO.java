package dao;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Static class used to get a user from the db
 */
public class UserDAO {
    /**
     * Runs a query to get the user with the corresponding username and password and Creates a User
     * object based on the results
     * @param username The username to match in the db
     * @param password The password to match in the db
     * @return Returns a user object if found, otherwise returns null
     */
    public static User getUser(String username, String password) {
        String query = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";

        try {
            Query.makeQuery(query);
            ResultSet result = Query.getResult();
            while (result.next()) {
                int idResult = result.getInt("User_ID");
                String usernameResult = result.getString("User_Name");
                String passwordResult = result.getString("Password");
                User user = new User(idResult, usernameResult, passwordResult);
                return user;
            }
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return null;
    }
}
