package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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

    public Label customerTableLabel;
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIdColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerAddressColumn;
    public TableColumn<Customer, String> customerDivisionColumn;
    public TableColumn<Customer, String> customerZipColumn;
    public TableColumn<Customer, String> customerPhoneColumn;
    public Button saveCustomerButton;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public Button menuCustomerButton;
    public Button customerClearButton;
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
        saveCustomerButton.setText(rb.getString("add"));
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

    public void saveCustomerClicked(ActionEvent actionEvent) {
        String id = customerIdField.getText();
        String name = customerNameField.getText();
        String address = customerAddressField.getText();
        Division division = customerDivisionCombo.getValue();
        String zip = customerZipField.getText();
        String phone = customerPhoneField.getText();

        if (division == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields cannot be empty.");
            alert.showAndWait();
        } else if (id.isEmpty()){
            CustomerDAO.addCustomer(name, address, division, zip, phone);
        } else {
            Customer customer = new Customer(Integer.parseInt(id), name, address, division, zip, phone);
            CustomerDAO.updateCustomer(customer);
        }

        customerTable.setItems(CustomerDAO.getCustomers());

        customerIdField.clear();
        customerNameField.clear();
        customerAddressField.clear();
        customerCountryCombo.getSelectionModel().clearSelection();
        customerCountryCombo.setPromptText("Country");
        customerDivisionCombo.getSelectionModel().clearSelection();
        customerDivisionCombo.setPromptText("Division");
        customerZipField.clear();
        customerPhoneField.clear();
    }

    public void updateCustomerClicked(ActionEvent actionEvent) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a customer from the list first.");
            alert.showAndWait();
        } else {
            customerIdField.setText(String.valueOf(customer.getId()));
            customerNameField.setText(customer.getName());
            customerAddressField.setText(customer.getAddress());
            customerCountryCombo.setValue(customer.getDivision().getCountry());
            customerDivisionCombo.setValue(customer.getDivision());
            customerZipField.setText(customer.getZip());
            customerPhoneField.setText(customer.getPhone());
        }
    }

    public void deleteCustomerClicked(ActionEvent actionEvent) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this customer?");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && choice.get().equals(ButtonType.OK))
            {
               CustomerDAO.deleteCustomer(customer);
            }
        }
        else // Display error if no selection
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }

        customerTable.setItems(CustomerDAO.getCustomers());
    }

    public void menuCustomerClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MenuForm.fxml"));
            loader.setResources(rb);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
            stage.setTitle(rb.getString("title"));
            stage.show();

        } catch (IOException e) {
            // If the fxml file is not found
            e.printStackTrace();
        }
    }

    public void onCountryCombo(ActionEvent actionEvent) {
        int countryId = customerCountryCombo.getValue().getId();

        customerDivisionCombo.setItems(DivisionDAO.getDivisions(countryId));
    }

    public void onCustomerClear(ActionEvent actionEvent) {
        customerIdField.clear();
        customerNameField.clear();
        customerAddressField.clear();
        customerCountryCombo.getSelectionModel().clearSelection();
        customerDivisionCombo.getSelectionModel().clearSelection();
        customerZipField.clear();
        customerPhoneField.clear();
    }
}
