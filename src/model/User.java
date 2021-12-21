package model;

/**
 * User model based on the db
 */
public class User {
    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Return the user id
     * @return The id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Set the user id
     * @param id Id to be assigned
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the username
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username
     * @param username The username to be assigned
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return the password
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     * @param password The password to be assigned
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
