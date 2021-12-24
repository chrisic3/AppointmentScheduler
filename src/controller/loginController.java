package controller;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controls the login screen functionality
 */
public class loginController implements Initializable {
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

    /**
     * Initializes the language for the login screen
     * @param url Not used
     * @param resourceBundle The language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        currentLabel.setText(rb.getString("currentLocale"));
        zoneIDLabel.setText(String.valueOf(Locale.getDefault()));
    }

    /**
     * Submits the supplied login credentials for verification,
     * loads menu screen if authenticated,
     * provides error message if invalid input
     * @param actionEvent Login button clicked
     */
    public void onLoginClick(ActionEvent actionEvent) {
        // Check that both fields have input
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            // Get user from db
            User user = UserDAO.getUser(usernameField.getText(), passwordField.getText());

            // Load the menu screen or provide an error
            if (user != null) {
                // Load the menu screen
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/menuForm.fxml"));
                    loader.setResources(rb);

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(loader.load());

                    stage.setScene(scene);
                    stage.setTitle("title");
                    stage.show();

                } catch (IOException e) {
                    // If the fxml file is not found
                    e.printStackTrace();
                }
            } else {
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
    }


}
