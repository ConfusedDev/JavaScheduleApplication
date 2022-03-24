package model;

/** Provides the data model for the cities used by the application and instantiates them. */
public class City {

    private int cityId;
    private String cityName;
    private int countryId;

    /** Instantiates a new city object with the entered parameters.
     * @param countryId Integer value storing the id of the country associated with the city.
     * @param cityId Integer value storing the id of the city.
     * @param cityName String object containing the name of the city. */
    public City(int cityId, String cityName, int countryId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryId = countryId;
    }

    /** Returns the city id.
     * @return Integer value of the city's id. */
    public int getCityId() {
        return cityId;
    }

    /** Returns the city's country id.
     * @return Integer value of the country id associated with the city. */
    public int getCountryId() {
        return countryId;
    }

    /** Sets the city id attribute.
     * @param cityId Integer value of the city id. */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /** Overrides the default implementation of the toString method to allow a city to return a name string.
     * @return String value of the name of the city associated with the record. */
    @Override
    public String toString(){
        return cityName;
    }
}
