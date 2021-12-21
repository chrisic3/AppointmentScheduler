package controller;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginMainController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    private Label zoneIDLabel;
    @FXML
    private Label currentLabel;
    @FXML
    public Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

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
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            User user = UserDAO.getUser(usernameField.getText(), passwordField.getText());

            if (user != null) {
                System.out.println("User id: " + user.getId() + "\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword());
                Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                success.setContentText("User id: " + user.getId() + "\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword());
                success.showAndWait();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText(rb.getString("userNotFound"));
                error.showAndWait();
            }
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText(rb.getString("invalidUserInput"));
            error.showAndWait();
        }
    }


}
