package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the Appointments form
 */
public class AppointmentController implements Initializable {
    // Appointment form variables
    @FXML
    private Label apptTableLabel;
    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<Appointment, Integer> apptIdColumn;
    @FXML
    private TableColumn<Appointment, String> apptTitleColumn;
    @FXML
    private TableColumn<Appointment, String> apptDescColumn;
    @FXML
    private TableColumn<Appointment, String> apptLocationColumn;
    @FXML
    private TableColumn<Appointment, String> apptContactColumn;
    @FXML
    private TableColumn<Appointment, String> apptTypeColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> apptStartColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> apptEndColumn;
    @FXML
    private TableColumn<Appointment, String> apptCustomerColumn;
    @FXML
    private TableColumn<Appointment, String> apptUserColumn;
    @FXML
    private TextField apptIdField;
    @FXML
    private TextField apptTitleField;
    @FXML
    private TextField apptDescField;
    @FXML
    private TextField apptLocationField;
    @FXML
    private DatePicker apptStartDate;
    @FXML
    private DatePicker apptEndDate;
    @FXML
    private ComboBox<Contact> apptContactCombo;
    @FXML
    private ComboBox<String> apptTypeCombo;
    @FXML
    private ComboBox<LocalTime> apptStartTimeCombo;
    @FXML
    private ComboBox<LocalTime> apptEndTimeCombo;
    @FXML
    private ComboBox<Customer> apptCustomerCombo;
    @FXML
    private ComboBox<User> apptUserCombo;
    @FXML
    private Button saveApptButton;
    @FXML
    private Button updateApptButton;
    @FXML
    private Button deleteApptButton;
    @FXML
    private Button menuApptButton;
    @FXML
    private Button clearApptButton;
    @FXML
    private ToggleGroup apptRadio;
    @FXML
    private RadioButton apptAllRadio;
    @FXML
    private RadioButton apptMonthRadio;
    @FXML
    private RadioButton apptWeekRadio;

    // Class variables
    private ResourceBundle rb;

    /**
     * Initializes the appointment form and sets the language, table, and comboboxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        // Set table
        apptTable.setItems(AppointmentDAO.getAppointments());

        // Set table columns
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        apptUserColumn.setCellValueFactory(new PropertyValueFactory<>("user"));

        // Set combo boxes
        apptContactCombo.setItems(ContactDAO.getContacts());
        apptTypeCombo.setItems(Appointment.getTypes());
        apptCustomerCombo.setItems(CustomerDAO.getCustomers());
        apptUserCombo.setItems(UserDAO.getUsers());

        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(0,0);
        do {
            apptStartTimeCombo.getItems().add(end);
            apptEndTimeCombo.getItems().add(end);
            end = end.plusHours(1);
        } while (start.isBefore(end));
    }

    /**
     * Adds appointment to db if id is empty, otherwise updates appointment
     * Checks for start being after end, and for times being during business hours
     * @param actionEvent Not used
     */
    public void saveApptClicked(ActionEvent actionEvent) {
        // Get input from fields/boxes
        String id = apptIdField.getText();
        String title = apptTitleField.getText();
        String description = apptDescField.getText();
        String location = apptLocationField.getText();
        Contact contact = apptContactCombo.getValue();
        String type = apptTypeCombo.getValue();
        LocalDateTime start = LocalDateTime.of(apptStartDate.getValue(), apptStartTimeCombo.getValue());
        LocalDateTime end = LocalDateTime.of(apptEndDate.getValue(), apptEndTimeCombo.getValue());
        Customer customer = apptCustomerCombo.getValue();
        User user = apptUserCombo.getValue();

        // Create business hours for given day
        LocalDateTime openDate = LocalDateTime.of(apptStartDate.getValue(), Appointment.getOpen());
        LocalDateTime endDate = LocalDateTime.of(apptEndDate.getValue(), Appointment.getClose());
        // Convert chosen times to EST for comparing to business hours
        LocalDateTime startEst = start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("US/Eastern")).toLocalDateTime();
        LocalDateTime endEst = end.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("US/Eastern")).toLocalDateTime();

        if (contact == null) {
            // Error if contact is empty (quick and dirty validation)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("emptyField"));
            alert.showAndWait();
        } else if (start.isAfter(end)) {
            // Error if start date/time is after end date/time
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Start time must be before end time.");
            alert.showAndWait();
            // Return to avoid clearing fields
            return;
        } else if ((startEst.isBefore(openDate)) || (endEst.isAfter(endDate))) {
            // Error if times before or after business times
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Time must be between 8 and 22 Eastern.");
            alert.showAndWait();
            // Return to avoid clearing fields
            return;
        } else if (id.isEmpty()){
            // Add new appointment
            // Id will be updated with generated id from db
            Appointment appointment = new Appointment(-1, title, description, location, contact, type, start, end, customer, user);
            AppointmentDAO.addAppointment(appointment);
        } else {
            // Update selected appointment
            Appointment appointment = new Appointment(Integer.parseInt(id), title, description, location, contact, type, start, end, customer, user);
            AppointmentDAO.updateAppointment(appointment);
        }

        // Update table to reflect changes
        apptTable.setItems(AppointmentDAO.getAppointments());

        // Clear all fields/boxes
        apptIdField.clear();
        apptTitleField.clear();
        apptDescField.clear();
        apptLocationField.clear();
        apptStartDate.setValue(null);
        apptEndDate.setValue(null);
        apptContactCombo.setValue(null);
        apptTypeCombo.setValue(null);
        apptStartTimeCombo.setValue(null);
        apptEndTimeCombo.setValue(null);
        apptCustomerCombo.setValue(null);
        apptUserCombo.setValue(null);
    }

    /**
     * Displays the selected appointment information in the fields/boxes for editing
     * @param actionEvent Not used
     */
    public void updateApptClicked(ActionEvent actionEvent) {
        // Get selected appointment
        Appointment appointment = apptTable.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            // Error if no selection
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select an appointment from the list first.");
            alert.showAndWait();
        } else {
            // Populate fields/boxes
            apptIdField.setText(String.valueOf(appointment.getId()));
            apptTitleField.setText(appointment.getTitle());
            apptDescField.setText(appointment.getDescription());
            apptLocationField.setText(appointment.getLocation());
            apptContactCombo.setValue(appointment.getContact());
            apptTypeCombo.setValue(appointment.getType());
            apptStartDate.setValue(appointment.getStart().toLocalDate());
            apptStartTimeCombo.setValue(appointment.getStart().toLocalTime());
            apptEndDate.setValue(appointment.getEnd().toLocalDate());
            apptEndTimeCombo.setValue(appointment.getEnd().toLocalTime());
            apptCustomerCombo.setValue(appointment.getCustomer());
            apptUserCombo.setValue(appointment.getUser());
        }
    }

    /**
     * Clears the input fields/boxes
     * @param actionEvent Not used
     */
    public void clearApptClicked(ActionEvent actionEvent) {
        apptIdField.clear();
        apptTitleField.clear();
        apptDescField.clear();
        apptLocationField.clear();
        apptStartDate.setValue(null);
        apptEndDate.setValue(null);
        apptContactCombo.setValue(null);
        apptTypeCombo.setValue(null);
        apptStartTimeCombo.setValue(null);
        apptEndTimeCombo.setValue(null);
        apptCustomerCombo.setValue(null);
        apptUserCombo.setValue(null);
    }

    /**
     * Deletes an appointment from the db
     * @param actionEvent Not used
     */
    public void deleteApptClicked(ActionEvent actionEvent) {
        // Get selected appointment
        Appointment appointment  = apptTable.getSelectionModel().getSelectedItem();

        if (appointment != null) {
            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && choice.get().equals(ButtonType.OK)) {
                AppointmentDAO.deleteAppointment(appointment);
            }
        }
        else {
            // Display error if no selection
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noSelection"));
            alert.showAndWait();
        }

        // Update table to reflect deletion
        apptTable.setItems(AppointmentDAO.getAppointments());
    }

    /**
     * Takes the user back to the main menu
     * @param actionEvent Not used
     */
    public void menuApptClicked(ActionEvent actionEvent) {
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

    public void onApptAllRadio(ActionEvent actionEvent) {
    }

    public void onApptMonthRadio(ActionEvent actionEvent) {
    }

    public void onApptWeekRadio(ActionEvent actionEvent) {
    }
}
