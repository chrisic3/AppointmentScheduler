package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import javafx.event.ActionEvent;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    public Label apptTableLabel;
    public TableView<Appointment> apptTable;
    public TableColumn<Appointment, Integer> apptIdColumn;
    public TableColumn<Appointment, String> apptTitleColumn;
    public TableColumn<Appointment, String> apptDescColumn;
    public TableColumn<Appointment, String> apptLocationColumn;
    public TableColumn<Appointment, String> apptContactColumn;
    public TableColumn<Appointment, String> apptTypeColumn;
    public TableColumn<Appointment, Timestamp> apptStartColumn;
    public TableColumn<Appointment, Timestamp> apptEndColumn;
    public TableColumn<Appointment, String> apptCustomerColumn;
    public TableColumn<Appointment, String> apptUserColumn;
    public TextField apptIdField;
    public TextField apptTitleField;
    public TextField apptDescField;
    public TextField apptLocationField;
    public DatePicker apptStartDate;
    public DatePicker apptEndDate;
    public ComboBox<Contact> apptContactCombo;
    public ComboBox<String> apptTypeCombo;
    public ComboBox<LocalTime> apptStartTimeCombo;
    public ComboBox<LocalTime> apptEndTimeCombo;
    public ComboBox<Customer> apptCustomerCombo;
    public ComboBox<User> apptUserCombo;
    public Button saveApptButton;
    public Button updateApptButton;
    public Button deleteApptButton;
    public Button menuApptButton;
    public Button clearApptButton;

    // Class variables
    private ResourceBundle rb;

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

        LocalTime start = Appointment.getOpen();
        LocalTime end = Appointment.getClose();
        while (start.isBefore(end.plusSeconds(1))) {
            apptStartTimeCombo.getItems().add(start);
            apptEndTimeCombo.getItems().add(start);
            start = start.plusHours(1);
        }
    }

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



        if (contact == null) {
            // Error if contact is empty (quick and dirty validation)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("emptyField"));
            alert.showAndWait();
        } else if (id.isEmpty()){
            // Add new appointment
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

    public void updateApptClicked(ActionEvent actionEvent) {
        // Get selected appointment
        Appointment appointment = apptTable.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            // Error if no selection
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noSelection"));
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

    public void deleteApptClicked(ActionEvent actionEvent) {
        // Get selected appointment
        Appointment appointment  = apptTable.getSelectionModel().getSelectedItem();

        if (appointment != null) {
            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("deleteConfirm"));
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
}
