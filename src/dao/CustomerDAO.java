package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Static class used for Customer db access
 */
public class CustomerDAO {
    /**
     * Gets all customers from the db
     * @return Returns an observable list of Customers
     */
    public static ObservableList<Customer> getCustomers() {
        String query = "SELECT c.Customer_ID, c.Customer_Name, c.Address, C.Postal_Code, c.Phone, d.Division_ID, " +
                "d.Division, d.Country_ID, co.Country FROM customers AS c LEFT JOIN first_level_divisions AS d ON " +
                "c.Division_ID = d.Division_ID LEFT JOIN countries AS co ON d.Country_ID = co.Country_ID";
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                Country country = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
                Division division = new Division(rs.getInt("Division_ID"), rs.getString("Division"), country);
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");

                customers.add(new Customer(id, name, address, division, zip, phone));
            }
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Gets a customer based on the provided id
     * @param idInput The customer id to look up
     * @return Returns a Customer
     */
    public static Customer getCustomer(int idInput) {
        String query = "SELECT c.Customer_ID, c.Customer_Name, c.Address, C.Postal_Code, c.Phone, d.Division_ID, " +
                "d.Division, d.Country_ID, co.Country FROM customers AS c LEFT JOIN first_level_divisions AS d ON " +
                "c.Division_ID = d.Division_ID LEFT JOIN countries AS co ON d.Country_ID = co.Country_ID WHERE " +
                "c.Customer_ID = ?";

        try {
            Query.makeSelectQuery(query, 1, String.valueOf(idInput));
            ResultSet rs = Query.getResult();

            rs.next();

            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            Country country = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
            Division division = new Division(rs.getInt("Division_ID"), rs.getString("Division"), country);
            String zip = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");

            return new Customer(id, name, address, division, zip, phone);
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds a customer to the db
     * @param customer The customer to add
     */
    public static void addCustomer(Customer customer) {
        String query = "INSERT INTO customers (Customer_Name, Address, Division_ID, Postal_Code, Phone) " +
                "VALUES(?, ?, ?, ?, ?)";

        Query.insertCustomer(query, customer);
        ResultSet rs = Query.getResult();

        try {
            rs.next();
            customer.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a customer in the db
     * @param customer The customer to update
     */
    public static void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Division_ID = ?, Postal_Code = ?, " +
                "Phone = ? WHERE Customer_ID = ?";

        Query.updateCustomer(query, customer);
    }

    /**
     * Deletes a customer from the db
     * @param customer The customer to delete
     */
    public static void deleteCustomer(Customer customer) {
        String query = "DELETE FROM customers WHERE Customer_ID = ?";

        Query.makeDeleteQuery(query, customer.getId());
    }
}
