package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class AppointmentDAO {

    public static ObservableList<Appointment> getAppointments() {
        String query = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, " +
                        "a.Start, a.End, cu.Customer_ID, u.User_ID, co.Contact_ID " +
                        "FROM appointments AS a LEFT JOIN customers AS cu ON " +
                        "a.Customer_ID = cu.Customer_ID LEFT JOIN users AS u ON " +
                        "a.User_ID = u.User_ID LEFT JOIN contacts AS co ON a.Contact_ID = co.Contact_ID;";
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            Query.makeSelectQuery(query, 0);
            ResultSet rs = Query.getResult();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                Contact contact = ContactDAO.getContact(rs.getInt("Contact_ID"));
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                Customer customer = CustomerDAO.getCustomer(rs.getInt("Customer_ID"));
                User user = UserDAO.getUser(rs.getInt("User_ID"));

                appointments.add(new Appointment(id, title, description, location, contact, type, start, end, customer, user));
            }
        } catch (SQLException e) {
            // Db error
            e.printStackTrace();
        }

        return appointments;
    }

    public static void addAppointment(Appointment appointment) {
        String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Query.insertAppointment(query, appointment);
        ResultSet rs = Query.getResult();

        try {
            rs.next();
            appointment.setId(rs.getInt(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAppointment(Appointment appointment) {
        String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        Query.updateAppointment(query, appointment);
    }

    public static void deleteAppointment(Appointment appointment) {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        int id = appointment.getId();
        String type = appointment.getType();

        Query.makeDeleteQuery(query, appointment.getId());

        // Display id and type of appointment
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setContentText("Appointment number " + id + " of type " + type + " was cancelled.");
        alert2.showAndWait();
    }
}