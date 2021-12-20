package dao;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static User getUser(String username, String password) {
        String query = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";

        try {
            Query.makeQuery(query);
            ResultSet result = Query.getResult();
            while (result.next()) {
                String idResult = result.getString("User_ID");
                String usernameResult = result.getString("User_Name");
                String passwordResult = result.getString("Password");
                User user = new User(idResult, usernameResult, passwordResult);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
