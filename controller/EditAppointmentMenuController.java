package controller;

import database.AppointmentDAO;
import database.CustomerDAO;
import database.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for editing appointments and the underlying data manipulations.*/
public class EditAppointmentMenuController implements Initializable {

    Stage stage;
    Parent scene;
    Appointment appointmentToEdit;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField titleTxt;


    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField urlTxt;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private ComboBox<Integer> startTimeComboBox;

    @FXML
    private ComboBox<Integer> endTimeComboBox;

    /** Checks to make sure fields have been entered and then updates the appointment record with new information.
    * @param event ActionEvent associated with the button being activated.
     *
     * Makes sure required fields are filled.
     * Converts meeting time from user time zone to UTC.
     * Prevents schedule overlap by checking for matching start times.
     * */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException {


        if(customerComboBox.getSelectionModel().isEmpty() || startTimeComboBox.getSelectionModel().isEmpty() ||
                datePicker.getEditor().getText().isEmpty() || titleTxt.getText().isEmpty() ||
                descriptionTxt.getText().isEmpty() || typeComboBox.getSelectionModel().isEmpty()
                || locationTxt.getText().isEmpty() || contactComboBox.getSelectionModel().isEmpty()
                || endTimeComboBox.getSelectionModel().isEmpty() || userComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Missing required fields!");
            alert.show();
            return;
        }

        int customerId = customerComboBox.getValue().getCustomerId();
        int userId = userComboBox.getValue().getUserId();
        int appointmentId = appointmentToEdit.getAppointmentId();
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String contact = contactComboBox.getValue();
        String type = typeComboBox.getSelectionModel().getSelectedItem();
        String url = urlTxt.getText();

        LocalDate meetingDate = datePicker.getValue();
        LocalTime startTime = LocalTime.of(startTimeComboBox.getSelectionModel().getSelectedItem(), 0);
        LocalTime endTime = LocalTime.of(endTimeComboBox.getSelectionModel().getSelectedItem(), 0);

        ZonedDateTime userStart = ZonedDateTime.of(meetingDate, startTime, ZoneId.systemDefault());
        ZonedDateTime userEnd = ZonedDateTime.of(meetingDate, endTime, ZoneId.systemDefault());
        Instant startInstant = userStart.toInstant();
        Instant endInstant = userEnd.toInstant();
        LocalDateTime utcStart = ZonedDateTime.ofInstant(startInstant, ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime utcEnd = ZonedDateTime.ofInstant(endInstant, ZoneId.of("UTC")).toLocalDateTime();


        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        for(Appointment a: allAppointments){
            if(a.getAppointmentId() != appointmentToEdit.getAppointmentId()
                    && LocalDateTime.of(meetingDate, startTime).isBefore(a.getEnd()) && a.getStart().isBefore(LocalDateTime.of(meetingDate, endTime))){
                Alert preventOverlapAlert = new Alert(Alert.AlertType.ERROR);
                preventOverlapAlert.setContentText("Trying to schedule overlapping meetings");
                preventOverlapAlert.show();
                return;
            }
        }

        AppointmentDAO.modifyAppointment(appointmentId, customerId, userId, title, description, location, contact, type, url, utcStart, utcEnd);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Filters end time combobox options so appointments are logical.
    * @param event ActionEvent associated with the combo box being activated. */
    @FXML
    void onActionFilterEndTimes(ActionEvent event) {

        int startTime = startTimeComboBox.getValue();
        ObservableList<Integer> endTimeOptions = FXCollections.observableArrayList();
        int closingTime = 22;
        for(int i = 0; i <= closingTime; i++){
            if(i > startTime) endTimeOptions.add(i);
        }
        endTimeComboBox.setItems(endTimeOptions);
    }

    /** Returns the user to the appointment main menu.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayAppointmentMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Applies the information about the currently selected appointment to the form automatically.
     * @param appointment Appointment object to populate the form data. */
    public void receiveAppointment(Appointment appointment){

        /** Populates the form with data to edit. */
        appointmentToEdit = appointment;
        titleTxt.setText(appointment.getTitle());
        descriptionTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        contactComboBox.getSelectionModel().select(appointment.getContact());
        urlTxt.setText(appointment.getUrl());
        typeComboBox.getSelectionModel().select(appointment.getType());
        datePicker.setValue(appointment.getStart().toLocalDate());
        startTimeComboBox.getSelectionModel().select((Integer) appointment.getStart().getHour());
        onActionFilterEndTimes(new ActionEvent());
        endTimeComboBox.getSelectionModel().select((Integer) appointment.getEnd().getHour());

        for(Customer c : customerComboBox.getItems()){
            if(c.getCustomerId()==appointment.getCustomerId()){
                customerComboBox.getSelectionModel().select(c);
            }
        }

        for(User u : userComboBox.getItems()){
            if(u.getUserId()==appointment.getUserId()){
                userComboBox.getSelectionModel().select(u);
            }
        }
    }


    /** Populates the form with options for scheduling a new appointment.
     *
     * @param url URL to be used by the initialization procedure.
     * @param rb ResourceBundle to be used by the initialization procedure.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        /** Initialized data for selections. */
        customerComboBox.setItems(CustomerDAO.getAllCustomers());

        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("Introduction");
        typeOptions.add("Follow-Up");
        typeOptions.add("Closing");
        typeComboBox.setItems(typeOptions);
        userComboBox.setItems(UserDAO.getAllUsers());

        ObservableList<String> contactOptions = FXCollections.observableArrayList();
        contactOptions.addAll("Test1", "Test2", "Test3");
        contactComboBox.setItems(contactOptions);

        /** Sets the available times to only allow business hours. */
        ObservableList<Integer> timeOptions = FXCollections.observableArrayList();
        timeOptions.addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        startTimeComboBox.setItems(timeOptions);

    }
}
