package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Country;
import model.Customer;
import model.Division;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the customers form
 */
public class CustomerController implements Initializable {

    public Label customerTableLabel;
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIdColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerAddressColumn;
    public TableColumn<Customer, String> customerDivisionColumn;
    public TableColumn<Customer, String> customerZipColumn;
    public TableColumn<Customer, String> customerPhoneColumn;
    public Button addCustomerButton;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public Button menuCustomerButton;
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public ComboBox<Country> customerCountryCombo;
    public ComboBox<Division> customerDivisionCombo;
    public TextField customerZipField;
    public TextField customerPhoneField;

    private ResourceBundle rb;

    /**
     * Initialize the language for the customer form
     * @param url Not used
     * @param resourceBundle Language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        customerTableLabel.setText(rb.getString("customers"));
        addCustomerButton.setText(rb.getString("add"));
        updateCustomerButton.setText(rb.getString("update"));
        deleteCustomerButton.setText(rb.getString("delete"));
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

        customerTable.setItems(CustomerDAO.getCustomers());

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerZipColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerCountryCombo.setItems(CountryDAO.getCountries());
    }

    public void addCustomerClicked(ActionEvent actionEvent) {
        String id = customerIdField.getText();
        String name = customerNameField.getText();
        String address = customerAddressField.getText();
        Division division = customerDivisionCombo.getValue();
        String zip = customerZipField.getText();
        String phone = customerPhoneField.getText();

        if (!id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Cannot add a selected user. Try \"Update\" instead.");
            alert.showAndWait();
        } else if (division == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields cannot be empty.");
            alert.showAndWait();
        } else {
            CustomerDAO.addCustomer(name, address, division, zip, phone);
        }

        customerTable.setItems(CustomerDAO.getCustomers());
    }

    public void updateCustomerClicked(ActionEvent actionEvent) {
    }

    public void deleteCustomerClicked(ActionEvent actionEvent) {
    }

    public void menuCustomerClicked(ActionEvent actionEvent) {
    }

    public void onCountryCombo(ActionEvent actionEvent) {
        int countryId = customerCountryCombo.getValue().getId();

        customerDivisionCombo.setItems(DivisionDAO.getDivisions(countryId));
    }
}
