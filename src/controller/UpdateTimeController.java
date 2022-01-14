package controller;

import dao.AppointmentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Controls the update time form
 */
public class UpdateTimeController implements Initializable {
    // Update time form variables
    @FXML
    private Label startTimeUpdateLabel;
    @FXML
    private Label endTimeUpdateLabel;
    @FXML
    private ComboBox<LocalTime> startTimeUpdateCombo;
    @FXML
    private ComboBox<LocalTime> endTimeUpdateCombo;
    @FXML
    private Button updateTimeSaveButton;
    @FXML
    private Button updateTimeCancelButton;

    // Class variables
    private ResourceBundle rb;
    private static Appointment appointment;
    private static AppointmentController apptController;

    /**
     * Initializes the window
     * @param url Not used
     * @param resourceBundle The language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        // Set language
        startTimeUpdateLabel.setText(rb.getString("startLabel"));
        endTimeUpdateLabel.setText(rb.getString("endLabel"));
        updateTimeSaveButton.setText(rb.getString("save"));
        updateTimeCancelButton.setText(rb.getString("cancel"));

        // Initialize time combo boxes
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(0,0);
        do {
            startTimeUpdateCombo.getItems().add(end);
            endTimeUpdateCombo.getItems().add(end);
            end = end.plusHours(1);
        } while (start.isBefore(end));

        startTimeUpdateCombo.getSelectionModel().select(appointment.getStart().toLocalTime());
        endTimeUpdateCombo.getSelectionModel().select(appointment.getEnd().toLocalTime());
    }

    /**
     * Sets the appointment reference
     * @param appt The appointment to set
     */
    public static void setAppointment(Appointment appt) {
        appointment = appt;
    }

    /**
     * Sets the appointment controller reference
     * @param controller The appointment controller to set
     */
    public static void setApptController(AppointmentController controller) {
        apptController = controller;
    }

    /**
     * Updates the start and end time in a new window
     * @param actionEvent The save button clicked
     */
    public void onUpdateTimeSave(ActionEvent actionEvent) {
        // Get input from boxes and convert to local date time
        LocalDateTime start = LocalDateTime.of(appointment.getStart().toLocalDate(), startTimeUpdateCombo.getValue());
        LocalDateTime end = LocalDateTime.of(appointment.getEnd().toLocalDate(), endTimeUpdateCombo.getValue());

        // Create business hours for given day
        LocalDateTime openDate = LocalDateTime.of(appointment.getStart().toLocalDate(), Appointment.getOpen());
        LocalDateTime closeDate = LocalDateTime.of(appointment.getEnd().toLocalDate(), Appointment.getClose());

        // Convert chosen times to EST for comparing to business hours
        LocalDateTime startEst = start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("US/Eastern")).toLocalDateTime();
        LocalDateTime endEst = end.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("US/Eastern")).toLocalDateTime();

        if (start.isAfter(end)) {
            // Error if start date/time is after end date/time
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("apptTimeError"));
            alert.showAndWait();
            // Return to avoid closing window
            return;
        } else if ((startEst.isBefore(openDate)) || (endEst.isAfter(closeDate))) {
            // Error if times before or after business times
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("apptBusinessTimeError"));
            alert.showAndWait();
            // Return to avoid closing window
            return;
        } else {
            appointment.setStart(start);
            appointment.setEnd(end);

            // If no overlap then add, else do nothing
            if (!apptController.hasOverlap(appointment)) {
                AppointmentDAO.addAppointment(appointment);
            }
        }

        // Refresh the table
        apptController.refreshTable();

        // Close the window
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Close the window
     * @param actionEvent The cancel button clicked
     */
    public void onUpdateTimeCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
