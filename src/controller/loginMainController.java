package controller;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginMainController implements Initializable {
    @FXML
    private Label zoneIDLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void OnSubmitLogin(ActionEvent actionEvent) throws IOException {
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
                error.setContentText("User not found.");
                error.showAndWait();
            }
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Username and/or password cannot be empty.");
            error.showAndWait();
        }
    }


}
