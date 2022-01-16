package controller;

import dao.AppointmentDAO;
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
import java.time.Month;
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
    private Label reportScheduleLabel;
    @FXML
    private Label reportCustomerCountLabel;
    @FXML
    private ComboBox<Month> reportMonthCombo;
    @FXML
    private ComboBox<String> reportTypeCombo;
    @FXML
    private ComboBox<Customer> reportCustomerCombo;
    @FXML
    private Button reportSelectMTButton;
    @FXML
    private Button reportScheduleButton;
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
        reportMTLabel.setText(rb.getString("monthType"));
        reportMonthCombo.setPromptText(rb.getString("month"));
        reportTypeCombo.setPromptText(rb.getString("type"));
        reportSelectMTButton.setText(rb.getString("select"));
        reportScheduleLabel.setText(rb.getString("contactSchedule"));
        reportScheduleButton.setText(rb.getString("schedule"));
        reportCustomerLabel.setText(rb.getString("customerAppt"));
        reportCustomerCombo.setPromptText(rb.getString("customer"));
        reportCustomerButton.setText(rb.getString("select"));
        reportMenuButton.setText(rb.getString("menu"));

        // Set combo boxes
        reportMonthCombo.setItems(getMonths());
        reportTypeCombo.setItems(Appointment.getTypes());
        reportCustomerCombo.setItems(CustomerDAO.getCustomers());
    }

    /**
     * Displays the number of appointments by given month and type
     */
    public void reportSelectMTButtonClicked() {
        Month month = reportMonthCombo.getValue();
        String type = reportTypeCombo.getValue();
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        int count = 0;

        for (Appointment appt : appointments) {
            if ((appt.getStart().getMonth().equals(month)) && (appt.getType().equals(type))) {
                count++;
            }
        }

        reportMTCountLabel.setText(String.valueOf(count));
    }

    /**
     * Opens the contact schedule window
     * @param actionEvent The schedule button clicked
     */
    public void scheduleButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ContactScheduleForm.fxml"));
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
     * Displays the number of appointments by customer
     */
    public void reportCustomerButtonClicked() {
        Customer customer = reportCustomerCombo.getValue();
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        int count = 0;

        for (Appointment appt : appointments) {
            if (appt.getCustomer().getId() == customer.getId()) {
                count++;
            }
        }

        reportCustomerCountLabel.setText(String.valueOf(count));
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
     * @return Returns an observable list of months
     */
    public ObservableList<Month> getMonths() {
        ObservableList<Month> months = FXCollections.observableArrayList();
        LocalDate month = LocalDate.now().withMonth(1);

        do {
            months.add(month.getMonth());
            month = month.plusMonths(1);
        } while (month.getMonth().getValue() != 1);

        return months;
    }
}
