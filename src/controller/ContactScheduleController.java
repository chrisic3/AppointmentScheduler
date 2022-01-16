package controller;

import com.mysql.cj.x.protobuf.MysqlxResultset;
import dao.AppointmentDAO;
import dao.ContactDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * Controls the contact schedule form
 */
public class ContactScheduleController implements Initializable {
    // Form variables
    @FXML
    private Label contactTableLabel;
    @FXML
    private TableView<Appointment> contactTable;
    @FXML
    private TableColumn<Appointment, Integer> contactIdColumn;
    @FXML
    private TableColumn<Appointment, String> contactTitleColumn;
    @FXML
    private TableColumn<Appointment, String> contactDescColumn;
    @FXML
    private TableColumn<Appointment, String> contactLocationColumn;
    @FXML
    private TableColumn<Appointment, String> contactContactColumn;
    @FXML
    private TableColumn<Appointment, String> contactTypeColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> contactStartColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> contactEndColumn;
    @FXML
    private TableColumn<Appointment, String> contactCustomerColumn;
    @FXML
    private TableColumn<Appointment, String> contactUserColumn;
    @FXML
    private ComboBox<Contact> contactContactCombo;
    @FXML
    private Button contactSelectButton;
    @FXML
    private Button contactAllButton;
    @FXML
    private Button contactMenuButton;

    // Local variables
    ResourceBundle rb;

    /**
     * Initializes the language, table, and combo box
     * @param url Not used
     * @param resourceBundle The language bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        // Set language
        contactTableLabel.setText(rb.getString("contactSchedule"));
        contactMenuButton.setText(rb.getString("menu"));
        contactIdColumn.setText(rb.getString("apptId"));
        contactTitleColumn.setText(rb.getString("apptTitle"));
        contactDescColumn.setText(rb.getString("apptDesc"));
        contactLocationColumn.setText(rb.getString("apptLocation"));
        contactContactColumn.setText(rb.getString("apptContact"));
        contactTypeColumn.setText(rb.getString("apptType"));
        contactStartColumn.setText(rb.getString("apptStart"));
        contactEndColumn.setText(rb.getString("apptEnd"));
        contactCustomerColumn.setText(rb.getString("apptCustomer"));
        contactUserColumn.setText(rb.getString("apptUser"));
        contactContactCombo.setPromptText(rb.getString("apptContact"));
        contactSelectButton.setText(rb.getString("select"));
        contactAllButton.setText(rb.getString("all"));

        // Set table
        contactTable.setItems(AppointmentDAO.getAppointments());

        // Set table columns
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        contactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        contactEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        contactCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        contactUserColumn.setCellValueFactory(new PropertyValueFactory<>("user"));

        // Set combo box
        contactContactCombo.setItems(ContactDAO.getContacts());
    }

    /**
     * Displays the list of appointments for selected contact
     */
    public void contactSelectButtonClicked() {
        Contact contact = contactContactCombo.getValue();
        ObservableList<Appointment> appts = AppointmentDAO.getAppointments();
        ObservableList<Appointment> curSelection = FXCollections.observableArrayList();

        for (Appointment appt : appts) {
            if (appt.getContact().getId() == contact.getId()) {
                curSelection.add(appt);
            }
        }

        contactTable.setItems(curSelection);
    }

    /**
     * Display all appointments if selected
     */
    public void contactAllButtonClicked() {
        contactTable.setItems(AppointmentDAO.getAppointments());
    }

    /**
     * Takes the user back to the main menu
     * @param actionEvent The menu button clicked
     */
    public void contactMenuClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MenuForm.fxml"));
            loader.setResources(rb);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
            stage.setTitle(rb.getString("title"));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            // If the fxml file is not found
            e.printStackTrace();
        }
    }
}
