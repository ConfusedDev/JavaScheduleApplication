package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Provides the data model for the users used by the application and instantiates them. */
public class User {

    private int userId;
    private String userName;
    private String password;
    private int active;
    private LocalDate createDate;
    private LocalTime createTime;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    /** Instantiates a new user object using the provided parameters.
     * @param userId Integer value storing the id of the user.
     * @param lastUpdateBy String object containing the name of the person that last updated the record.
     * @param lastUpdate LocalDateTime object containing the date and time the object was last updated.
     * @param createTime LocalTime object storing the time that the user record was created.
     * @param createdBy String object storing the name of the user that created the record.
     * @param createDate LocalDate object containing the date that the user was created.
     * @param active Integer value associated with the boolean value of the user being active within the database.
     * @param password String object containing the password of the user.
     * @param userName String object containing the username of the user. */
    public User(int userId, String userName, String password, int active, LocalDate createDate, LocalTime createTime,
                String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /** Returns the username string.
     * @return String object containing the username of the user. */
    public String getUserName() {
        return userName;
    }

    /** Returns the password string.
     * @return String object containing the password of the user. */
    public String getPassword() {
        return password;
    }

    /** Returns the user's id.
     * @return Integer value of the user's id. */
    public int getUserId() {
        return userId;
    }

    /** Overrides the default toString method to return user's name.
     * @return String value of the user's username. */
    @Override
    public String toString(){
        return getUserName();
    }

}
