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
     * Adds a customer to the db
     * @param name The customer name
     * @param address The customer address
     * @param division The customer division
     * @param zip The customer zip
     * @param phone The customer phone
     */
    public static void addCustomer(String name, String address, Division division, String zip, String phone) {
        String query = "INSERT INTO customers (Customer_Name, Address, Division_ID, Postal_Code, Phone) " +
                "VALUES(?, ?, ?, ?, ?)";

        Query.makeInsertQuery(query, 5, name, address, String.valueOf(division.getId()), zip, phone);
    }

    /**
     * Updates a customer in the db
     * @param customer The customer to update
     */
    public static void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Division_ID = ?, Postal_Code = ?, " +
                "Phone = ? WHERE Customer_ID = ?";

        Query.makeUpdateQuery(query, 6, customer.getName(), customer.getAddress(),
                String.valueOf(customer.getDivision().getId()), customer.getZip(), customer.getPhone(),
                String.valueOf(customer.getId()));
    }

    /**
     * Deletes a customer from the db
     * @param customer The customer to delete
     */
    public static void deleteCustomer(Customer customer) {
        String query = "DELETE FROM customers WHERE Customer_ID = ?";

        Query.makeDeleteQuery(query, customer);
    }
}
