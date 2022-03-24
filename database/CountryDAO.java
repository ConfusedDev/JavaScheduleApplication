package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Handles communication with the sql database and processes user requests. */
public class CountryDAO {

    /** Returns an observable list of all the country entries in the database.
     * @return ObservableList containing all of the country records from the database. */
    public static ObservableList<Country> getAllCountries(){

        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * FROM country";
            PreparedStatement ps = DatabaseConnection.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String countryName = rs.getString("country");
                int countryId = rs.getInt("countryId");

                Country country1 = new Country(countryId, countryName);
                allCountries.add(country1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return allCountries;
    }

}