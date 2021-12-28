package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Static class used for Country db access
 */
public class CountryDAO {
    /**
     * Gets all countries from db
     * @return Returns an observable list of Countries
     */
    public static ObservableList<Country> getCountries() {
        String query = "SELECT Country_ID, Country FROM countries";
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                countries.add(new Country(id, country));
            }
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }
        return countries;
    }
}
