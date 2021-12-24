import controller.loginController;
import dao.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Initiates the application and loads the login window
 */
public class Main extends Application {
    /**
     * Main method changes default locale, opens db connection, launches application, then closes
     * the db connection
     * @param args Cmd line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr", "FR"));
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * Start method initiates the login window and starts the application
     * @param primaryStage Initial stage
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the resource bundle for the application
        ResourceBundle rb = ResourceBundle.getBundle("languages/Lang");

        try {
            // Load xml file and set the resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/loginForm.fxml"));
            loader.setResources(rb);

            // Set up the scene
            Scene scene = new Scene(loader.load());

            // Set and show the scene
            primaryStage.setScene(scene);
            primaryStage.setTitle(rb.getString("title"));
            primaryStage.show();
        } catch (IOException e) {
            // If the fxml file is not found
            e.printStackTrace();
        }
    }
}