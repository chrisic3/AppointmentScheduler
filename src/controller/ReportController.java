package controller;

import dao.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controlls the reports form
 */
public class ReportController implements Initializable {
    // Form variables
    @FXML
    private Label reportMTLabel;
    @FXML
    private Label reportMTCountLabel;
    @FXML
    private Label reportCustomerLabel;
    @FXML
    private Label scheduleLabel;
    @FXML
    private Label reportCustomerCountLabel;
    @FXML
    private ComboBox<String> reportMonthCombo;
    @FXML
    private ComboBox<String> reportTypeCombo;
    @FXML
    private ComboBox<Customer> reportCustomerCombo;
    @FXML
    private Button reportSelectMTButton;
    @FXML
    private Button scheduleButton;
    @FXML
    private Button reportCustomerButton;
    @FXML
    private Button reportMenuButton;

    // Local variables
    ResourceBundle rb;

    /**
     * Initializes the report form language and combo boxes
     * @param url Not used
     * @param resourceBundle The language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        // Set Language


        // Set combo boxes
        reportMonthCombo.setItems(getMonths());
        reportTypeCombo.setItems(Appointment.getTypes());
        reportCustomerCombo.setItems(CustomerDAO.getCustomers());
    }

    public void reportSelectMTButtonClicked(ActionEvent actionEvent) {
    }

    public void scheduleButtonClicked(ActionEvent actionEvent) {
    }

    public void reportCustomerButtonClicked(ActionEvent actionEvent) {
    }

    /**
     * Return to the main menu
     * @param actionEvent The menu button clicked
     */
    public void reportMenuButtonClicked(ActionEvent actionEvent) {
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
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of months
     * @return Returns an observable list of strings
     */
    public ObservableList<String> getMonths() {
        return FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December");
    }
}
