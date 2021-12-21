package controller;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.User;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controls the login window functionality
 */
public class loginMainController implements Initializable {
    // Form elements
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
     * Submits the supplied login credentials for verification, prompts if error with input
     * @param actionEvent Login button clicked
     */
    public void OnSubmitLogin(ActionEvent actionEvent) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/test.fxml"));
//        Parent root = loader.load();
//
//        Test test = loader.getController();
//        test.receiveData(usernameField, passwordField);
//
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();
        // Check that both fields have input
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            // Get user from db
            User user = UserDAO.getUser(usernameField.getText(), passwordField.getText());

            // TODO
            if (user != null) {
                System.out.println("User id: " + user.getId() + "\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword());
                Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                success.setContentText("User id: " + user.getId() + "\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword());
                success.showAndWait();
            } else {
                // TODO
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
