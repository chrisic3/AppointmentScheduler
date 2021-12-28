package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Static class for Division db access
 */
public class DivisionDAO {
    /**
     * Gets all divisions from the db that matches the provided country id
     * @param countryId The id to filter down the divisions
     * @return Returns an observable list of divisions
     */
    public static ObservableList<Division> getDivisions(int countryId) {
        String query = "SELECT d.Division_ID, d.Division, c.Country_ID, c.Country FROM first_level_divisions AS d LEFT JOIN countries AS c " +
                "ON d.Country_ID = c.Country_ID WHERE d.Country_ID = ?";

        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            Query.makeSelectQuery(query, 1, String.valueOf(countryId));
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int dId = rs.getInt("Division_ID");
                String dName = rs.getString("Division");
                int cId = rs.getInt("Country_ID");
                String cName = rs.getString("Country");

                Country country = new Country(cId, cName);
                Division division = new Division(dId, dName, country);

                divisions.add(division);
            }
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return divisions;
    }
}
