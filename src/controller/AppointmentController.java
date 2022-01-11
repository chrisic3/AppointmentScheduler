package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import javafx.collections.FXCollections;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
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
     * @param url Not user
     * @param resourceBundle Language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        // Set language
        apptTableLabel.setText(rb.getString("appointments"));
        saveApptButton.setText(rb.getString("save"));
        updateApptButton.setText(rb.getString("update"));
        deleteApptButton.setText(rb.getString("delete"));
        clearApptButton.setText(rb.getString("clear"));
        menuApptButton.setText(rb.getString("menu"));
        apptIdColumn.setText(rb.getString("apptId"));
        apptTitleColumn.setText(rb.getString("apptTitle"));
        apptDescColumn.setText(rb.getString("apptDesc"));
        apptLocationColumn.setText(rb.getString("apptLocation"));
        apptContactColumn.setText(rb.getString("apptContact"));
        apptTypeColumn.setText(rb.getString("apptType"));
        apptStartColumn.setText(rb.getString("apptStart"));
        apptEndColumn.setText(rb.getString("apptEnd"));
        apptCustomerColumn.setText(rb.getString("apptCustomer"));
        apptUserColumn.setText(rb.getString("apptUser"));
        apptAllRadio.setText(rb.getString("apptAllRadio"));
        apptMonthRadio.setText(rb.getString("apptMonthRadio"));
        apptWeekRadio.setText(rb.getString("apptWeekRadio"));
        apptIdField.setPromptText(rb.getString("apptId"));
        apptTitleField.setPromptText(rb.getString("apptTitle"));
        apptDescField.setPromptText(rb.getString("apptDesc"));
        apptLocationField.setPromptText(rb.getString("apptLocation"));
        apptStartDate.setPromptText(rb.getString("apptStartDate"));
        apptStartTimeCombo.setPromptText(rb.getString("apptStartTime"));
        apptEndDate.setPromptText(rb.getString("apptEndDate"));
        apptEndTimeCombo.setPromptText(rb.getString("apptEndTime"));
        apptContactCombo.setPromptText(rb.getString("apptContact"));
        apptTypeCombo.setPromptText(rb.getString("apptType"));
        apptCustomerCombo.setPromptText(rb.getString("apptCustomer"));
        apptUserCombo.setPromptText(rb.getString("apptUser"));

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
            alert.setContentText(rb.getString("apptTimeError"));
            alert.showAndWait();
            // Return to avoid clearing fields
            return;
        } else if ((startEst.isBefore(openDate)) || (endEst.isAfter(endDate))) {
            // Error if times before or after business times
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("apptBusinessTimeError"));
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
            alert.setContentText(rb.getString("apptNoSelection"));
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("apptDeleteConfirm"));
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && choice.get().equals(ButtonType.OK)) {
                AppointmentDAO.deleteAppointment(appointment, rb);
            }
        }
        else {
            // Display error if no selection
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("apptNoSelection"));
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

    /**
     * Display all appointments if selected
     * @param actionEvent Not used
     */
    public void onApptAllRadio(ActionEvent actionEvent) {
        apptTable.setItems(AppointmentDAO.getAppointments());
    }

    /**
     * Display appointments for current month if selected
     * @param actionEvent Not used
     */
    public void onApptMonthRadio(ActionEvent actionEvent) {
        // Get current month
        Month currentMonth = LocalDate.now().getMonth();

        ObservableList<Appointment> appts = AppointmentDAO.getAppointments();
        ObservableList<Appointment> curAppts = FXCollections.observableArrayList();


        appts.forEach(appt -> {
            if (appt.getStart().getMonth().equals(currentMonth)) {
                curAppts.add(appt);
            }
        });

        apptTable.setItems(curAppts);
    }

    /**
     * Display appointments for current week if selected
     * @param actionEvent Not used
     */
    public void onApptWeekRadio(ActionEvent actionEvent) {
        LocalDate firstDayOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        ObservableList<Appointment> appts = AppointmentDAO.getAppointments();
        ObservableList<Appointment> curAppts = FXCollections.observableArrayList();

        appts.forEach(appt -> {
            // Using the start date of the appointment only because even if it ends in a later week,
            // it should show up in the current week, which is when it starts.
            LocalDate start = appt.getStart().toLocalDate();

            if ((start.isAfter(firstDayOfWeek) && (start.isBefore(lastDayOfWeek)))) {
                curAppts.add(appt);
            }
        });

        apptTable.setItems(curAppts);
    }
}
