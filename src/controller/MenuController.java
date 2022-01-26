package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controls menu screen
 */
public class MenuController implements Initializable {
    // Form element variables
    @FXML
    private Button customersMenuButton;
    @FXML
    private Button appointmentsMenuButton;
    @FXML
    private Button reportMenuButton;

    // Class variables
    private ResourceBundle rb;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Initializes the language for the screen and local variables
     * @param url Not used
     * @param resourceBundle The language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        this.appointments = AppointmentDAO.getAppointments();

        customersMenuButton.setText(rb.getString("customers"));
        appointmentsMenuButton.setText(rb.getString("appointments"));
        reportMenuButton.setText(rb.getString("reports"));


        // Check for and alert user of appointments within 15 minutes
        reminder(appointments);
    }

    /**
     * Opens the customers screen
     * @param actionEvent Customers button clicked
     */
    public void onCustomersClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CustomerForm.fxml"));
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
     * Opens the appointments screen
     * @param actionEvent Appointments button clicked
     */
    public void onAppointmentsClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AppointmentForm.fxml"));
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

    public void onReportsClick(ActionEvent actionEvent) {
    }

    /**
     * Loops through the list of appointments and alerts the user if they have one within 15 minutes from their
     * system date/time.
     * @param appts The list of appointments
     */
    public void reminder(ObservableList<Appointment> appts) {
        // Convert local time to UTC to compare
        LocalDateTime systemTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        for (Appointment appt : appts) {
            // Convert local time to UTC to compare
            LocalDateTime apptStart = appt.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

            if ((systemTime.isAfter(apptStart.minusMinutes(15))) && (systemTime.isBefore(apptStart))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(rb.getString("upcoming") + appt.getId() + ", " + appt.getStart().toLocalDate() + ", " + appt.getStart().toLocalTime());
                alert.showAndWait();
            }
        }
    }
}
