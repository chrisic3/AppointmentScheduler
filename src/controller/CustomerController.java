package controller;

import dao.AppointmentDAO;
import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the customers form
 */
public class CustomerController implements Initializable {
    // Customer form variables
    @FXML
    private Label customerTableLabel;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, String> customerDivisionColumn;
    @FXML
    private TableColumn<Customer, String> customerZipColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private Button saveCustomerButton;
    @FXML
    private Button updateCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button menuCustomerButton;
    @FXML
    private Button customerClearButton;
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private ComboBox<Country> customerCountryCombo;
    @FXML
    private ComboBox<Division> customerDivisionCombo;
    @FXML
    private TextField customerZipField;
    @FXML
    private TextField customerPhoneField;

    // Class variables
    private ResourceBundle rb;

    /**
     * Initialize the language for the customer form and sets the table and country combobox
     * @param url Not used
     * @param resourceBundle Language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        // Language
        customerTableLabel.setText(rb.getString("customers"));
        saveCustomerButton.setText(rb.getString("save"));
        updateCustomerButton.setText(rb.getString("update"));
        deleteCustomerButton.setText(rb.getString("delete"));
        customerClearButton.setText(rb.getString("clear"));
        menuCustomerButton.setText(rb.getString("menu"));
        customerIdColumn.setText(rb.getString("customerId"));
        customerNameColumn.setText(rb.getString("customerName"));
        customerAddressColumn.setText(rb.getString("customerAddress"));
        customerDivisionColumn.setText(rb.getString("customerDivision"));
        customerPhoneColumn.setText(rb.getString("customerPhone"));
        customerIdField.setPromptText(rb.getString("customerId"));
        customerNameField.setPromptText(rb.getString("customerName"));
        customerAddressField.setPromptText(rb.getString("customerAddress"));
        customerCountryCombo.setPromptText(rb.getString("customerCountry"));
        customerDivisionCombo.setPromptText(rb.getString("customerDivision"));
        customerZipField.setPromptText(rb.getString("customerZip"));
        customerPhoneField.setPromptText(rb.getString("customerPhone"));

        // Set table
        customerTable.setItems(CustomerDAO.getCustomers());

        // Set table columns
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerZipColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Set country box
        customerCountryCombo.setItems(CountryDAO.getCountries());
    }

    /**
     * Adds a customer to the db if the id is empty, otherwise updates the selected customer
     */
    public void saveCustomerClicked() {
        // Get input from fields/boxes
        String id = customerIdField.getText();
        String name = customerNameField.getText();
        String address = customerAddressField.getText();
        Division division = customerDivisionCombo.getValue();
        String zip = customerZipField.getText();
        String phone = customerPhoneField.getText();

        if (division == null) {
            // Error if division is empty (quick and dirty validation)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("emptyField"));
            alert.showAndWait();
        } else if (id.isEmpty()){
            // Add new customer
            // Id will be updated with generated id from db
            Customer customer = new Customer(-1, name, address, division, zip, phone);
            CustomerDAO.addCustomer(customer);
        } else {
            // Update selected customer
            Customer customer = new Customer(Integer.parseInt(id), name, address, division, zip, phone);
            CustomerDAO.updateCustomer(customer);
        }

        // Update table to reflect changes
        customerTable.setItems(CustomerDAO.getCustomers());

        // Clear all fields/boxes
        customerIdField.clear();
        customerNameField.clear();
        customerAddressField.clear();
        customerCountryCombo.setValue(null);
        customerDivisionCombo.setValue(null);
        customerDivisionCombo.setItems(null);
        customerZipField.clear();
        customerPhoneField.clear();
    }

    /**
     * Displays the selected customer information in the fields/boxes for editing
     */
    public void updateCustomerClicked() {
        // Get selected customer
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            // Error if no selection
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noCustomerSelection"));
            alert.showAndWait();
        } else {
            // Populate fields/boxes
            customerIdField.setText(String.valueOf(customer.getId()));
            customerNameField.setText(customer.getName());
            customerAddressField.setText(customer.getAddress());
            customerCountryCombo.setValue(customer.getDivision().getCountry());
            customerDivisionCombo.setValue(customer.getDivision());
            customerZipField.setText(customer.getZip());
            customerPhoneField.setText(customer.getPhone());
        }
    }

    /**
     * Deletes a customer from the db and all associated appointments.
     * A lambda is used here to loop through each appointment and delete each one the
     * customer is attached to.
     */
    public void deleteCustomerClicked() {
        // Get selected customer
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null) {
            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("deleteCustomerConfirm"));
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && choice.get().equals(ButtonType.OK)) {
                // Get all appointments
                ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
                // Loop through appointments and find where appointments.customer_id = customer.id
                // Lambda to loop through appointments and delete ones attached to given customer
                appointments.forEach(appt -> {
                    if (appt.getCustomer().getId() == customer.getId()) {
                        // Delete each matching appointment
                        AppointmentDAO.deleteAppointment(appt, rb);
                    }
                });

                CustomerDAO.deleteCustomer(customer);
            }
        }
        else {
        // Display error if no selection
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(rb.getString("noCustomerSelection"));
        alert.showAndWait();
        }

        // Update table to reflect deletion
        customerTable.setItems(CustomerDAO.getCustomers());
    }

    /**
     * Clears the input fields/boxes
     */
    public void onCustomerClear() {
        customerIdField.clear();
        customerNameField.clear();
        customerAddressField.clear();
        customerCountryCombo.setValue(null);
        customerDivisionCombo.setValue(null);
        customerDivisionCombo.setItems(null);
        customerZipField.clear();
        customerPhoneField.clear();
    }

    /**
     * Takes the user back to the main menu
     * @param actionEvent The menu button clicked
     */
    public void menuCustomerClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MenuForm.fxml"));
            loader.setResources(rb);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
            stage.setTitle(rb.getString("title"));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            // If the fxml file is not found
            e.printStackTrace();
        }
    }

    /**
     * Sets the division box based on the id of the country
     */
    public void onCountryCombo() {

        int countryId = customerCountryCombo.getValue().getId();

        customerDivisionCombo.setItems(DivisionDAO.getDivisions(countryId));
    }
}
