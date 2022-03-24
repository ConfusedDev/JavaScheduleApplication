package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Handles communication with the sql database and processes user requests. */
public class UserDAO {

    /** Returns an observable list of all users from the database.
     * @return ObservableList of all user records from the database. */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM user";
            PreparedStatement ps = DatabaseConnection.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                int active = rs.getInt("active");
                LocalDate createDate = rs.getDate("createDate").toLocalDate();
                LocalTime createTime = rs.getTime("createDate").toLocalTime();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
                String lastUpdateBy = rs.getString("lastUpdateBy");

                User user1 = new User(userId, userName, password, active, createDate, createTime, createdBy, lastUpdate,
                        lastUpdateBy);
                allUsers.add(user1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return allUsers;
    }
}
