package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Query {
    private static ResultSet result;

    public static void makeQuery(String query) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getResult() {
        return result;
    }
}
