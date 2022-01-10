package model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Appointment model based on db
 */
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

    // Business hours
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

    /**
     * Gets the appointment's id
     * @return Returns the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the appointment's id
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the appointment's title
     * @return Returns the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment's title
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the appointment's description
     * @return Returns the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment's description
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the appointment's location
     * @return Returns the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the appointment's location
     * @param location The location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the appointment's contact
     * @return Returns the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets the appointment's contact
     * @param contact The contact to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Gets the appointment's type
     * @return Returns the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment's type
     * @param type The type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment's start date/time
     * @return Returns the start date/time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the appointment's start date/time
     * @param start The start date/time to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the appointment's end date/time
     * @return Returns the end date/time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the appointment's end date/time
     * @param end The end date/time to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets the appointment's customer
     * @return Returns the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the appointment's customer
     * @param customer The customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the appointment's user
     * @return Returns the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the appointment's user
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the business open time
     * @return Returns the business open time
     */
    public static LocalTime getOpen() {
        return OPEN;
    }

    /**
     * Gets the business close time
     * @return Returns the business close time
     */
    public static LocalTime getClose() {
        return CLOSE;
    }

    /**
     * Gets the list of types
     * @return Returns the observable list of types
     */
    public static ObservableList<String> getTypes() {
        return FXCollections.observableArrayList("First meeting", "Second meeting", "Third meeting");
    }
}
