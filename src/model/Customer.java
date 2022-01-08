package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Customer model based on db
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private Division division;
    private String zip;
    private String phone;

    public Customer(int id, String name, String address, Division division, String zip, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.division = division;
        this.zip = zip;
        this.phone = phone;
    }

    /**
     * Returns the customer's id
     * @return Returns the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer's id
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the customer's name
     * @return Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the customer's address
     * @return Returns the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address
     * @param address The address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the customer's division
     * @return Returns the division
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Sets the customer's division
     * @param division The division to set
     */
    public void setDivision(Division division) {
        this.division = division;
    }

    /**
     * Returns the customer's zip
     * @return Returns the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the customer's zip
     * @param zip The zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Returns the customer's phone
     * @return Returns the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone
     * @param phone The phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name;
    }
}
