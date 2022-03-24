package model;

import java.time.LocalDateTime;

/** Provides the data model for the appointments used by the application and instantiates them. */
public class Appointment {

    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;

    /** Instantiates a new appointment with provided parameters.
     * @param customerId Integer value of the customer's id associated with the appointment.
     * @param appointmentId Integer value of the appointment's id.
     * @param contact String containing the value of the contact at the associated business.
     * @param description String object containing the description of the scheduled appointment.
     * @param end LocalDateDate containing the date and time that the appointment is scheduled to end.
     * @param location String object containing the location of the scheduled appointment.
     * @param start LocalDateTime object containing the date and time that the appointment is scheduled to start.
     * @param title String object containing the title of the scheduled appointment.
     * @param type String object containing the type of appointment scheduled.
     * @param url String object containing the url associated with the appointment.
     * @param userId Integer value containing the id of the user associated with the appointment. */
    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location,
                       String contact, String type, String url, LocalDateTime start, LocalDateTime end) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
    }

    /** Returns appointment id.
     * @return Integer value of the appointment's id. */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** Returns customer id associated with the appointment.
     * @return Integer value of the customer id associated with the appointment. */
    public int getCustomerId() {
        return customerId;
    }

    /** Returns the title of the appointment.
     * @return String object containing the title of the appointment. */
    public String getTitle() {
        return title;
    }

    /** Returns the description text of the appointment.
     * @return  String object containing a description of the scheduled appointment. */
    public String getDescription() {
        return description;
    }

    /** Returns the location text for the schedule appointment.
     * @return String object containing a description of the appointment location. */
    public String getLocation() {
        return location;
    }

    /** Returns the contact string for the appointment.
     * @return String object containing the contact for the appointment. */
    public String getContact() {
        return contact;
    }

    /** Returns the appointment type.
     * @return String object containing the type of appointment scheduled. */
    public String getType() {
        return type;
    }

    /** Sets the appointment type attribute.
     * @param type String object containing the type of appointment. */
    public void setType(String type) {
        this.type = type;
    }

    /** Returns the url for the appointment.
     * @return String object containing the url for the scheduled appointment. */
    public String getUrl() {
        return url;
    }

    /** Sets the url field for the appointment object.
     * @param url String object containing the url associated with the appointment. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** Returns the end date and time for the appointment.
     * @return LocalDateTime object containing the value of the end of the appointment. */
    public LocalDateTime getEnd() {
        return end;
    }

    /** Returns the start date and time for the appointment.
     * @return LocalDateTime object containing the value of the start of the appointment. */
    public LocalDateTime getStart() {
        return start;
    }

    /** Returns the user id for the appointment.
     * @return Integer value of the user id associated with the appointment. */
    public int getUserId() {
        return userId;
    }
}
