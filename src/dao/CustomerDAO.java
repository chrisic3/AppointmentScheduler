package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
//    private static

    public static ObservableList<Customer> getCustomers() {
        String query = "SELECT c.Customer_ID, c.Customer_Name, c.Address, C.Postal_Code, c.Phone, d.Division " +
                "FROM customers AS c LEFT JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID";
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String division = rs.getString("Division");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");

                customers.add(new Customer(id, name, address, division, zip, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static void addCustomer(String name, String address, Division division, String zip, String phone) {
        String query = "INSERT INTO customers (Customer_Name, Address, Division_ID, Postal_Code, Phone) " +
                "VALUES(?, ?, ?, ?, ?)";

        Query.makeInsertQuery(query, 5, name, address, String.valueOf(division.getId()), zip, phone);
    }

    public static void updateCustomer() {
    }
}
