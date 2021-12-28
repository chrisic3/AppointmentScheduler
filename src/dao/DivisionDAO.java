package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();

    public static ObservableList<Division> getDivisions(int countryId) {
        String query = "SELECT d.Division_ID, d.Division, c.Country_ID, c.Country FROM first_level_divisions AS d LEFT JOIN countries AS c " +
                "ON d.Country_ID = c.Country_ID WHERE d.Country_ID = ?";

        divisions.clear();

        try {
            Query.makeSelectQuery(query, 1, String.valueOf(countryId));
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int cId = rs.getInt("Country_ID");
                String cName = rs.getString("Country");

                divisions.add(new Division(id, division, new Country(cId, cName)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisions;
    }
}
