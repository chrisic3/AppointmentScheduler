package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Test implements Initializable {
    public TextField usernameTest;
    public TextField passwordTest;

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/loginForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene((root)));
        stage.show();
    }

    public void receiveData(TextField usernameField, PasswordField passwordField) {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        usernameTest.setText(user);
        passwordTest.setText(pass);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
