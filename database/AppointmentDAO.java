package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** Handles communication with the sql database and processes user requests. */
public class AppointmentDAO {

    /** Retrieves an observable list of all appointments on the calendar.
     * @return ObservableList of the appointment records from the database. */
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointment";
            PreparedStatement ps = DatabaseConnection.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                int userId = rs.getInt("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String contact = rs.getString("contact");
                String type = rs.getString("type");
                String url = rs.getString("url");

                String utcStartDateTime = rs.getString("start");
                String utcEndDateTime = rs.getString("end");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                ZonedDateTime utcZonedStartDateTime = ZonedDateTime.of(LocalDateTime.parse(utcStartDateTime, dateTimeFormatter), ZoneId.of("UTC"));
                ZonedDateTime utcZonedEndDateTime = ZonedDateTime.of(LocalDateTime.parse(utcEndDateTime, dateTimeFormatter), ZoneId.of("UTC"));
                LocalDateTime start = utcZonedStartDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime end = utcZonedEndDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


                Appointment appointment1 = new Appointment(appointmentId, customerId, userId, title, description, location,
                        contact, type, url, start, end);

                allAppointments.add(appointment1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return allAppointments;
    }

    /** Enters a new appointment into the database.
     * @param userId Integer value of the user's id associated with the appointment.
     * @param url String object containing the url associated with the appointment.
     * @param type String object containing the type of appointment.
     * @param title String object containing the title of the appointment.
     * @param start LocalDateTime object containing the date and time of the start of the appointment.
     * @param location String object containing the location of the appointment.
     * @param end LocalDateTime object containing the date and time of the end of the appointment.
     * @param description String object containing a description of the scheduled appointment.
     * @param contact String object containing the contact associated with the appointment.
     * @param customerId Integer value containing the id of the customer associated with the appointment.
     * @return Boolean value associated with the success of the operation. */
    public static boolean createAppointment(int customerId, int userId, String title, String description, String location,
                                            String contact, String type, String url, LocalDateTime start, LocalDateTime end){
        try{
            String sql1 = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type," +
                    "url, start, end, createDate, createdBy, lastUpdateBy) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps1 = DatabaseConnection.getConn().prepareStatement(sql1);

            ps1.setInt(1, customerId);
            ps1.setInt(2, userId);
            ps1.setString(3, title);
            ps1.setString(4, description);
            ps1.setString(5, location);
            ps1.setString(6, contact);
            ps1.setString(7, type);
            ps1.setString(8, url);
            ps1.setString(9, start.toString());
            ps1.setString(10, end.toString());
            ps1.setString(11, LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
            ps1.setString(12, "test");
            ps1.setString(13, "test");

            ps1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } return true;

    }

    /** Updates an appointment record with new information.
     * @param userId Integer value of the user's id associated with the appointment.
     * @param url String object containing the url associated with the appointment.
     * @param type String object containing the type of appointment.
     * @param title String object containing the title of the appointment.
     * @param start LocalDateTime object containing the date and time of the start of the appointment.
     * @param location String object containing the location of the appointment.
     * @param end LocalDateTime object containing the date and time of the end of the appointment.
     * @param description String object containing a description of the scheduled appointment.
     * @param contact String object containing the contact associated with the appointment.
     * @param customerId Integer value containing the id of the customer associated with the appointment.
     * @param appointmentId Integer value of the appointment's id.
     * @return Boolean value associated with the success of the operation. */
    public static boolean modifyAppointment(int appointmentId, int customerId, int userId, String title, String description,
                                            String location, String contact, String type, String url,
                                            LocalDateTime start, LocalDateTime end){
        try{
            String sql1 = "UPDATE appointment SET customerId = ?, userId = ?, title = ?, description = ?," +
                    "location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = ?," +
                    "lastUpdateBy = ? WHERE appointmentId = ?";
            PreparedStatement ps1 = DatabaseConnection.getConn().prepareStatement(sql1);
            ps1.setInt(1, customerId);
            ps1.setInt(2, userId);
            ps1.setString(3, title);
            ps1.setString(4, description);
            ps1.setString(5, location);
            ps1.setString(6, contact);
            ps1.setString(7, type);
            ps1.setString(8, url);
            ps1.setString(9, start.toString());
            ps1.setString(10, end.toString());
            ps1.setString(11, ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime().toString());
            ps1.setString(12, "test");
            ps1.setInt(13, appointmentId);
            ps1.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } return true;

    }

    /** Deletes an appointment record from the database.
     * @param appointmentId Integer value of the appointment id to be removed from the database.
     * @return Boolean value associated with the success of the operation. */
    public static boolean deleteAppointment(int appointmentId){

        try {
            String sql1 = "DELETE FROM appointment WHERE appointmentId = ?";
            PreparedStatement ps1 = DatabaseConnection.getConn().prepareStatement(sql1);
            ps1.setInt(1, appointmentId);
            ps1.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } return true;
    }
}
