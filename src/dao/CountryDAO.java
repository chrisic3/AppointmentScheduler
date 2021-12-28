package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    private static ObservableList<Country> countries = FXCollections.observableArrayList();

    public static ObservableList<Country> getCountries() {
        String query = "SELECT Country_ID, Country FROM countries";

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                countries.add(new Country(id, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
}
