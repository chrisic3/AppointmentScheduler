package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String division;
    private String zip;
    private String phone;

    public Customer(int id, String name, String address, String division, String zip, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.division = division;
        this.zip = zip;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
