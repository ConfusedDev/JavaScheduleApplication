package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.City;
import utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Handles communication with the sql database and processes user requests. */
public class CityDAO {

    /** Returns an observable list of all the city entries in the database.
     * @return ObservableList of all city records from the database. */
    public static ObservableList<City> getAllCities(){

        ObservableList<City> allCities = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * FROM city";
            PreparedStatement ps = DatabaseConnection.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String cityName = rs.getString("city");
                int cityId = rs.getInt("cityId");
                int countryId = rs.getInt("countryId");

                City city1 = new City(cityId, cityName, countryId);
                allCities.add(city1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return allCities;
    }

}
