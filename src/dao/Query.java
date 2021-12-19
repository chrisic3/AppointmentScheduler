package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Query {
//    private static String query;
    private static Statement statement;
    private static ResultSet result;

    public static void makeQuery(String query) {
        try {
            statement = DBConnection.conn.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getResult() {
        return result;
    }
}
