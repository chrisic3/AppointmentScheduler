package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls menu screen
 */
public class menuController implements Initializable {
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

    public void onCustomersClick(ActionEvent actionEvent) {
    }

    public void onAppointmentsClick(ActionEvent actionEvent) {
    }

    public void onReportsClick(ActionEvent actionEvent) {
    }


}
