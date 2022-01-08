package controller;

import dao.AppointmentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

import java.net.URL;
import java.sql.Timestamp;
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
    public ComboBox apptContactCombo;
    public ComboBox apptTypeCombo;
    public ComboBox apptStartTimeCombo;
    public ComboBox apptEndTimeCombo;
    public ComboBox apptCustomerCombo;
    public ComboBox apptUserCombo;
    public Button saveApptButton;
    public Button updateApptButton;
    public Button deleteApptButton;
    public Button menuApptButton;
    public Button clearApptButton;

    // Class variables
    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    }

    public void onApptContactCombo(ActionEvent actionEvent) {
    }

    public void onApptTypeCombo(ActionEvent actionEvent) {
    }

    public void onAptStartDate(ActionEvent actionEvent) {
    }

    public void onApptStartTimeCombo(ActionEvent actionEvent) {
    }

    public void onApptEndDate(ActionEvent actionEvent) {
    }

    public void onApptEndTimeCombo(ActionEvent actionEvent) {
    }

    public void onApptCustomerCombo(ActionEvent actionEvent) {
    }

    public void onApptUserCombo(ActionEvent actionEvent) {
    }

    public void saveApptClicked(ActionEvent actionEvent) {
    }

    public void updateApptClicked(ActionEvent actionEvent) {
    }

    public void clearApptClicked(ActionEvent actionEvent) {
    }

    public void deleteApptClicked(ActionEvent actionEvent) {
    }

    public void menuApptClicked(ActionEvent actionEvent) {
    }
}
