package controller;

import dao.AppointmentDAO;
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
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controls the login screen functionality
 */
public class LoginController implements Initializable {
    // Form element variables
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label zoneIDLabel;
    @FXML
    private Label currentLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    // Class variables
    private ResourceBundle rb;
    String filename = "login_activity.txt";
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Initializes the language for the login screen and local variables
     * @param url Not used
     * @param resourceBundle The language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        this.appointments = AppointmentDAO.getAppointments();

        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        currentLabel.setText(rb.getString("currentZone"));
        zoneIDLabel.setText(String.valueOf(ZoneId.systemDefault()));
    }

    /**
     * Submits the supplied login credentials for verification,
     * loads menu screen if authenticated,
     * provides error message if invalid input.
     * Alerts user about upcoming appointments if successful.
     * @param actionEvent Login button clicked
     */
    public void onLoginClick(ActionEvent actionEvent) {
        try {
            // Get time for log-in file
            Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

            // File output
            FileWriter fw = new FileWriter(filename, true);
            PrintWriter pw = new PrintWriter(fw);

            // Get username from text field for logging
            String username = usernameField.getText();

            // Check that both fields have input
            if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                // Get user from db
                User user = UserDAO.getUser(usernameField.getText(), passwordField.getText());

                // Load the menu screen or provide an error
                if (user != null) {
                    // Load the menu screen
                    try {
                        // Log log-in
                        pw.println("User " + user.getUsername() + " successfully logged in at " + currentTime + " UTC");

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MenuForm.fxml"));
                        loader.setResources(rb);

                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(loader.load());

                        stage.setScene(scene);
                        stage.setTitle(rb.getString("title"));
                        stage.show();
                        stage.centerOnScreen();

                        // Check for and alert user of appointments within 15 minutes
                        reminder(appointments);
                    } catch (IOException e) {
                        // If the fxml file is not found
                        e.printStackTrace();
                    }
                } else {
                    // Log failed Log-in
                    pw.println("User " + username + " gave an invalid log-in at " + currentTime + " UTC");

                    // Error message if user is not found in the db
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText(rb.getString("userNotFound"));
                    error.showAndWait();
                }
            } else {
                // Username, password, or both are empty
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(rb.getString("invalidUserInput"));
                error.showAndWait();
            }

            pw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loops through the list of appointments and alerts the user if they have one within 15 minutes from their
     * system date/time. Otherwise, it tells them they do not have any.
     * @param appts The list of appointments
     */
    public void reminder(ObservableList<Appointment> appts) {
        boolean hasAppt = false;

        // Convert local time to UTC to compare
        LocalDateTime systemTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        for (Appointment appt : appts) {
            // Convert local time to UTC to compare
            LocalDateTime apptStart = appt.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

            if ((systemTime.isAfter(apptStart.minusMinutes(15))) && (systemTime.isBefore(apptStart))) {
                hasAppt = true;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(rb.getString("upcoming") + appt.getId() + ", " + appt.getStart().toLocalDate() + ", " + appt.getStart().toLocalTime());
                alert.showAndWait();
            }
        }

        if (!hasAppt) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(rb.getString("noUpcoming"));
            alert.showAndWait();
        }
    }
}
