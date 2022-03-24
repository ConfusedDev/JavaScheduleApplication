package controller;

import database.AppointmentDAO;
import database.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utils.TotalInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for the calendar and the underlying data manipulations. */
public class CalendarViewController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;

    @FXML
    private Label customerReportLabel;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> appointmentEndCol;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    /** Populates the table with all appointments.
     * @param event ActionEvent associated with the radio button being activated. */
    @FXML
    void handleAllToggle(ActionEvent event) {

        //Retrieves all appointments.**/
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        appointmentTableView.setItems(allAppointments);
    }

    /** Populates the table with appointments for the next month.
     * @param event ActionEvent associated with the radio button being activated. */
    @FXML
    void handleMonthlyToggle(ActionEvent event) {

        //Retrieves table entries that occur within the next month
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        LocalDateTime monthFromNow = LocalDateTime.now().plus(Period.ofMonths(1));
        for(Appointment appointment : allAppointments){
            if(appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(monthFromNow)){
                monthlyAppointments.add(appointment);
            }
        }
        appointmentTableView.setItems(monthlyAppointments);
    }

    /** Populates the table with appointments for the next week.
    * @param event ActionEvent associated with the radio button being activated. */
    @FXML
    void handleWeeklyToggle(ActionEvent event) {

        //Retrieves table entries that occur within the next week
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        LocalDateTime weekFromNow = LocalDateTime.now().plus(Period.ofWeeks(1));
        for(Appointment appointment : allAppointments){
            if(appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(weekFromNow)){
                weeklyAppointments.add(appointment);
            }
        }
        appointmentTableView.setItems(weeklyAppointments);
    }

    /** Generates a report for meetings by month and type.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionGenerateTypesReport(ActionEvent event) {

        Alert typesReport = new Alert(Alert.AlertType.INFORMATION);
        typesReport.setHeaderText("Currently Scheduled Meetings by Month and Type");
        typesReport.setTitle("Types Report");
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        String reportContent = "";
        for(int i = 1; i <= 12; i++){
            int introductions = 0;
            int followUps = 0;
            int closings = 0;
            int total = 0;
            for(Appointment a : allAppointments){
                if(a.getStart().getMonth().getValue()==i){
                    if(a.getType().equalsIgnoreCase("introduction")){
                        introductions++;
                    } else if(a.getType().equalsIgnoreCase("follow-up")){
                        followUps++;
                    } else closings++;
                    total ++;
                }
            }
            reportContent = reportContent + Month.of(i).toString() + " Total Meetings: "+total+" Introductions: "
                    + introductions+ " Follow-Ups: "+ followUps + " Closings: "+ closings+ "\n";
        }
        typesReport.setContentText(reportContent);
        typesReport.show();
    }

    /** Returns the window to the main menu.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Populates the table with a report of only selected customers meetings.
    * @param event ActionEvent associated with the button being activated.
     *
    * Uses stream and lambda expressions to generate customer schedule report on table efficiently without need for another window. */
    @FXML
    void onActionGetCustomerSchedule(ActionEvent event) throws NullPointerException{

        if(appointmentTableView.getSelectionModel().isEmpty()){
            Alert selectCustomerAlert = new Alert(Alert.AlertType.ERROR);

            selectCustomerAlert.setContentText("Select a customer appointment from the table to generate report within the table for that customer.");
            selectCustomerAlert.setTitle("Select a Customer");
            selectCustomerAlert.setHeaderText("Select a Customer");
            selectCustomerAlert.show();
        }

        /** Uses stream and lambda expressions to generate customer schedule report on table efficiently without need for another window. */
        ObservableList<Appointment> customerSchedule = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        allAppointments.stream()
                .filter(a->a.getCustomerId()==appointmentTableView.getSelectionModel().getSelectedItem().getCustomerId())
                .forEach(a->customerSchedule.add(a));
        appointmentTableView.setItems(customerSchedule);

    }

    @FXML
    void onActionGetContactSchedule(ActionEvent event) throws NullPointerException{

        if(appointmentTableView.getSelectionModel().isEmpty()){
            Alert selectContactAlert = new Alert(Alert.AlertType.ERROR);

            selectContactAlert.setContentText("Select a contact appointment from the table to generate report within the table for that contact.");
            selectContactAlert.setTitle("Select a Contact");
            selectContactAlert.setHeaderText("Select a Contact");
            selectContactAlert.show();
        }

        /** Uses stream and lambda expressions to generate customer schedule report on table efficiently without need for another window. */
        ObservableList<Appointment> contactSchedule = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        allAppointments.stream()
                .filter(a->a.getContact().equalsIgnoreCase(appointmentTableView.getSelectionModel().getSelectedItem().getContact()))
                .forEach(a->contactSchedule.add(a));
        appointmentTableView.setItems(contactSchedule);

    }

    /**
     * @param url URL object to be used by the initialization procedure.
     * @param rb ResourceBundle object to be used by the initialization procedure.
     *
     *Lambda expression for reporting total customers.
     *Adds appointment data to the calendar.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();

        appointmentTableView.setItems(allAppointments);
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));

    /** Lambda expression for reporting total customers efficiently. */
        TotalInterface totalInterface = o -> o.size();
        int customerCounter = totalInterface.total(CustomerDAO.getAllCustomers());
        customerReportLabel.setText("Your Total Customers: "+customerCounter);
    }

}
