package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    /**
     * Initializes the language for the screen
     * @param url Not used
     * @param resourceBundle The language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        customersMenuButton.setText(rb.getString("customers"));
        appointmentsMenuButton.setText(rb.getString("appointments"));
        reportMenuButton.setText(rb.getString("reports"));
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


}
