package model;

/**
 * Contact model based on db
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the contact's id
     * @return Returns the contact's id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the contact's id
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the contact's name
     * @return Returns the contact's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the contact's name
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the contact's email
     * @return Returns the contact's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the contact's email
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Overridden to display contact name
     * @return Returns the contact name
     */
    @Override
    public String toString() {
        return name;
    }
}
