package controller;

import database.AppointmentDAO;
import database.CustomerDAO;
import database.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for signing in and the underlying data manipulations. */
public class LoginMenuController implements Initializable {

    Stage stage;
    Parent scene;
    Alert userAlert = new Alert(Alert.AlertType.ERROR);
    Alert passAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private Label passwordLbl;

    @FXML
    private Label userNameLbl;

    @FXML
    private Button exitBtn;

    @FXML
    private Button signInBtn;

    @FXML
    private TextField userNameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label userTimeZoneLbl;

    /** Checks to make sure the user and password match and if the user has any appointments in 15 minutes and then displays the main menu.
    * @param event ActionEvent associated with the button being activated.
     *
     * Checks for meetings within 15 minutes.
     * Enters user and time data into the log file.
     * */
    @FXML
    void onActionSignIn(ActionEvent event) throws IOException {

        ObservableList<User> userList = UserDAO.getAllUsers();
        String userName = userNameTxt.getText();
        String pass = passwordTxt.getText();

        for(User user : userList){

            if(user.getUserName().equalsIgnoreCase(userName) && user.getPassword().equals(pass)){

                check();

                String filename = "src/utils/login_activity.txt";
                FileWriter logger = new FileWriter(filename, true);
                PrintWriter pw = new PrintWriter(logger);
                pw.println(user.getUserName()+" logged in at: "+ LocalDateTime.now().toString());
                logger.close();
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else if(user.getUserName().equalsIgnoreCase(userName) && !user.getPassword().equals(pass)){
                passAlert.show();
            }
            else {
                userAlert.show();
            }

        }

    }

    /** Closes the application.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionExit(ActionEvent event) {
    System.exit(0);
    }

    /** Checks for meeting within 15 minutes. */
    public void check(){
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        for(Appointment a: allAppointments){
            if (a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plus(Duration.ofMinutes(15)))){
                Alert meetingAlert = new Alert(Alert.AlertType.INFORMATION);
                ObservableList<Customer> allCustomers = CustomerDAO.getAllCustomers();
                for(Customer c : allCustomers){
                    if(c.getCustomerId() == a.getCustomerId()){
                        String customerName = c.getCustomerName();
                        meetingAlert.setContentText("You have a meeting with "+ customerName +" starting within 15 minutes");
                    }
                }
                meetingAlert.show();
                return;
            }
        }
        Alert noMeetingAlert = new Alert(Alert.AlertType.INFORMATION);
        noMeetingAlert.setContentText("You have no upcoming meetings within 15 minutes.");
        noMeetingAlert.show();
    }

    /** Translates the log in form and exception control messages into english or french based on default user locale.
     *
     * @param url URL to be used by the initialization procedure.
     * @param rb ResourceBundle to be used by the initialization procedure.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        /** Translates the log in form between english and french. */
        rb = ResourceBundle.getBundle("i18n/Login");
        passwordLbl.setText(rb.getString("password"));
        userNameLbl.setText(rb.getString("username"));
        exitBtn.setText(rb.getString("exit"));
        signInBtn.setText(rb.getString("signin"));
        passAlert.setContentText(rb.getString("passwordalert"));
        passAlert.setTitle(rb.getString("passwordalert"));
        userAlert.setContentText(rb.getString("useralert"));
        userAlert.setTitle(rb.getString("useralert"));

        userTimeZoneLbl.setText(ZonedDateTime.now().getZone().getDisplayName(TextStyle.FULL, Locale.getDefault()));

    }
}
