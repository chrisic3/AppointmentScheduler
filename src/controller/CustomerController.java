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
        customerIdColumn.setText(rb.getString("id"));
        customerNameColumn.setText(rb.getString("name"));
        customerAddressColumn.setText(rb.getString("address"));
        customerDivisionColumn.setText(rb.getString("division"));
        customerPhoneColumn.setText(rb.getString("phone"));
        customerIdField.setPromptText(rb.getString("id"));
        customerNameField.setPromptText(rb.getString("name"));
        customerAddressField.setPromptText(rb.getString("address"));
        customerCountryCombo.setPromptText(rb.getString("country"));
        customerDivisionCombo.setPromptText(rb.getString("division"));
        customerZipField.setPromptText(rb.getString("zip"));
        customerPhoneField.setPromptText(rb.getString("phone"));

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
     * @param actionEvent Not used
     */
    public void saveCustomerClicked(ActionEvent actionEvent) {
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
     * @param actionEvent Not used
     */
    public void updateCustomerClicked(ActionEvent actionEvent) {
        // Get selected customer
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            // Error if no selection
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noSelection"));
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
     * Deletes a customer from the db and all associated appointments
     * @param actionEvent Not used
     */
    public void deleteCustomerClicked(ActionEvent actionEvent) {
        // Get selected customer
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null) {
            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("deleteConfirm"));
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && choice.get().equals(ButtonType.OK)) {
                // Get all appointments
                ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
                // Loop through appointments and find where appointments.customer_id = customer.id
                // LAMBDA
                appointments.forEach(appt -> {
                    if (appt.getCustomer().getId() == customer.getId()) {
                        // Delete each matching appointment
                        AppointmentDAO.deleteAppointment(appt);
                    }
                });

                CustomerDAO.deleteCustomer(customer);
            }
        }
        else {
        // Display error if no selection
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(rb.getString("noSelection"));
        alert.showAndWait();
        }

        // Update table to reflect deletion
        customerTable.setItems(CustomerDAO.getCustomers());
    }

    /**
     * Clears the input fields/boxes
     * @param actionEvent Not used
     */
    public void onCustomerClear(ActionEvent actionEvent) {
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
     * @param actionEvent Not used
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
     * @param actionEvent Not used
     */
    public void onCountryCombo(ActionEvent actionEvent) {

        int countryId = customerCountryCombo.getValue().getId();

        customerDivisionCombo.setItems(DivisionDAO.getDivisions(countryId));
    }
}
