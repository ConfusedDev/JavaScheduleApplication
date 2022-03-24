package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Handles communication with the sql database and processes user requests. */
public class CustomerDAO {

    /** Returns an observable list of all customer records from the database.
     * @return ObservableList of all customer records from the database. */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT customerId, customerName, customer.addressId, address, address2, city, country, postalCode, phone, active," +
                    " customer.createDate, customer.createdBy, customer.lastUpdate, customer.lastUpdateBy FROM customer," +
                    " address, city, country WHERE customer.addressId = address.addressId" +
                    " AND address.cityId = city.cityId AND city.countryId = country.countryId";
            PreparedStatement ps = DatabaseConnection.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                String address = rs.getString("address");
                String address2 =rs.getString("address2");
                String city = rs.getString("city");
                String country = rs.getString("country");
                String postalCode = rs.getString("postalCode");
                String phone = rs.getString("phone");
                int active = rs.getInt("active");
                LocalDate createDate = rs.getDate("customer.createDate").toLocalDate();
                LocalTime createTime = rs.getTime("customer.createDate").toLocalTime();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("customer.lastUpdate").toLocalDateTime();
                String lastUpdateBy = rs.getString("customer.lastUpdateBy");

                Customer customer1 = new Customer(customerId, customerName, addressId, address, address2, city, country, postalCode,
                        phone, active, createDate, createTime, createdBy, lastUpdate, lastUpdateBy);

                allCustomers.add(customer1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return allCustomers;
    }


    /** Creates a new customer record in the database.
     * @param cityId Integer value of the city id associated with the customer record.
     * @param customerName String object containing the name of the customer.
     * @param customerAddress String object containing the address of the customer.
     * @param customerAddress2 String object containing the second line of address information.
     * @param customerPhone String object containing the customer's phone number.
     * @param customerPostalCode String object containing the customer's postal code.
     * @return Boolean value associated with the success of the operation. */
    public static boolean createCustomer(String customerName, String customerAddress, String customerAddress2,
                                         String customerPostalCode, String customerPhone, int cityId){
        try{
            String sql1 = "INSERT INTO address(address, address2, postalCode, phone, cityId, createDate, createdBy, lastUpdateBy)" +
                    " VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps1 = DatabaseConnection.getConn().prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);

            ps1.setString(1, customerAddress);
            ps1.setString(2, customerAddress2);
            ps1.setString(3, customerPostalCode);
            ps1.setString(4, customerPhone);
            ps1.setInt(5, cityId);
            ps1.setString(6, LocalDateTime.now().toString());
            ps1.setString(7, "test");
            ps1.setString(8, "test");

            ps1.execute();
            ResultSet rs = ps1.getGeneratedKeys();
            rs.next();
            int addressId = rs.getInt(1);

            String sql2 = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy)" +
                    " VALUES (?,?,?,?,?,?)";
            PreparedStatement ps2 = DatabaseConnection.getConn().prepareStatement(sql2);

            ps2.setString(1, customerName);
            ps2.setInt(2, addressId);
            ps2.setInt(3, 1);
            ps2.setString(4,LocalDateTime.now().toString());
            ps2.setString(5, "test");
            ps2.setString(6, "test");

            ps2.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } return true;

    }

    /** Updates a customer record in the database with new information.
     * @param cityId Integer value of the city id associated with the customer record.
     * @param customerId Integer value of the customer id to be modified within the database.
     * @param addressId Integer value of the address id associated with the customer record.
     * @param customerName String object containing the name of the customer.
     * @param customerAddress String object containing the address of the customer.
     * @param customerAddress2 String object containing the second line of address information.
     * @param customerPhone String object containing the customer's phone number.
     * @param customerPostalCode String object containing the customer's postal code.
     * @return Boolean value associated with the success of the operation. */
    public static boolean modifyCustomer(int customerId, int addressId, String customerName, String customerAddress,
                                         String customerAddress2, String customerPostalCode, String customerPhone,
                                         int cityId){
        try{
            String sql1 = "UPDATE customer SET customerName = ?, lastUpdateBy = ? WHERE customerId = ?";
            PreparedStatement ps1 = DatabaseConnection.getConn().prepareStatement(sql1);
            ps1.setString(1, customerName);
            ps1.setString(2, "test");
            ps1.setInt(3, customerId);
            ps1.execute();

            String sql2 = "UPDATE address SET address = ?, address2 = ?, postalCode = ?, phone = ?, cityId = ?," +
                    "lastUpdateBy = ? WHERE addressId = ?";
            PreparedStatement ps2 = DatabaseConnection.getConn().prepareStatement(sql2);
            ps2.setString(1, customerAddress);
            ps2.setString(2, customerAddress2);
            ps2.setString(3, customerPostalCode);
            ps2.setString(4, customerPhone);
            ps2.setInt(5, cityId);
            ps2.setString(6, "test");
            ps2.setInt(7, addressId);
            ps2.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } return true;

    }

    /** Deletes a customer record from the database.
     * @param customerId Integer value of the customer's id.
     * @param addressId Integer value of the address id associated with the customer record.
     * @return Boolean value associated with the success of the operation.
     */
    public static boolean deleteCustomer(int customerId, int addressId){
        try{
            String sql1 = "DELETE FROM appointment WHERE customerId = ?";
            PreparedStatement ps1 = DatabaseConnection.getConn().prepareStatement(sql1);
            ps1.setInt(1, customerId);
            ps1.execute();

            String sql2 = "DELETE FROM customer WHERE customerId = ?";
            PreparedStatement ps2 = DatabaseConnection.getConn().prepareStatement(sql2);
            ps2.setInt(1, customerId);
            ps2.execute();

            String sql3 = "DELETE FROM address WHERE addressId = ?";
            PreparedStatement ps3 = DatabaseConnection.getConn().prepareStatement(sql3);
            ps3.setInt(1, addressId);
            ps3.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } return true;
    }

}
