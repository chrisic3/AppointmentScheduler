package model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private Contact contact;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private Customer customer;
    private User user;

    private final static LocalTime OPEN = LocalTime.of(8, 0);
    private final static LocalTime CLOSE = LocalTime.of(22,0);

    public Appointment(int id, String title, String description, String location, Contact contact, String type, LocalDateTime start, LocalDateTime end, Customer customerId, User userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer = customerId;
        this.user = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static LocalTime getOpen() {
        return OPEN;
    }

    public static LocalTime getClose() {
        return CLOSE;
    }

    public static ObservableList<String> getTypes() {
        return FXCollections.observableArrayList("First meeting", "Second meeting", "Third meeting");
    }
}
