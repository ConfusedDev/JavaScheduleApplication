package model;

/** Data model for the country objects used in the application. */
public class Country {

    private int countryId;
    private String country;

    /** Creates a new country object and initializes country id and name.
     * @param country String object containing the country's name.
     * @param countryId Integer value of the country's id. */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /** Returns the country id.
     * @return Integer value of the country's id. */
    public int getCountryId() {
        return countryId;
    }

    /** Returns the country's name.
     * @return String value of the country's name. */
    public String getCountry() {
        return country;
    }

    /** Overrides the default implementation of toString to return the country's name.
     * @return String value of the name representing the country object. */
    @Override
    public String toString(){
        return getCountry();
    }
}
