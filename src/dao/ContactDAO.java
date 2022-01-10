package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Static class used for Appointment db access
 */
public class ContactDAO {
    /**
     * Gets all contacts from the db
     * @return Returns an observable list of Contacts
     */
    public static ObservableList<Contact> getContacts() {
        String query = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                contacts.add(new Contact(id, name, email));
            }
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * Gets a contact from the db based on id
     * @param idInput The id to lookup
     * @return Returns a Contact
     */
    public static Contact getContact(int idInput) {
        String query = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = " + idInput;

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            rs.next();
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            return new Contact(id, name, email);
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return null;
    }
}
