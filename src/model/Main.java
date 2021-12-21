package model;

import dao.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ResourceBundle rb = ResourceBundle.getBundle("languages/Lang");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/loginMainForm.fxml"));
            loader.setResources(rb);

            Parent root = loader.load();

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle(rb.getString("title"));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr", "FR"));
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}