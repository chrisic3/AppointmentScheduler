package model;

/**
 * Country model based on db
 */
public class Country {
    private int id;
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the country's id
     * @return Returns the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the country's id
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the country's name
     * @return Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the country's name
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Overridden to return the country's name
     * @return Returns the name
     */
    @Override
    public String toString() {
        return name;
    }
}
