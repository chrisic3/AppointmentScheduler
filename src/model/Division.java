package model;

/**
 * Division model based on db
 */
public class Division {
    private int id;
    private String name;
    private Country country;

    public Division(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    /**
     * Returns the division's id
     * @return Returns the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the division's id
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the division's name
     * @return Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the division's name
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the division's country
     * @return Returns the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the division's country
     * @param country The country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Overridden to return the division's name
     * @return Returns the name
     */
    @Override
    public String toString() {
        return name;
    }
}
