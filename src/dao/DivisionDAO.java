package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();

    public static ObservableList<Division> getDivisions(int countryId) {
        String query = "SELECT d.Division_ID, d.Division FROM first_level_divisions AS d LEFT JOIN countries AS c " +
                "ON d.Country_ID = c.Country_ID WHERE d.Country_ID = ?";

        divisions.clear();

        try {
            Query.makeSelectQuery(query, 1, String.valueOf(countryId));
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                divisions.add(new Division(id, division));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisions;
    }
}
