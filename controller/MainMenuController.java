package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for navigating the application and the underlying data manipulations. */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    /** Opens up the customer main menu.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayCustomerMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Opens up the appointment main menu.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayAppointmentMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Opens up the calendar view.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayCalendarView(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CalendarView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Exits the application.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionExit(ActionEvent event) {
    System.exit(0);
    }

    /** Default implementation of initialize.
     *
     * @param url URL to be used by the initialization procedure.
     * @param rb ResourceBundle to be used by the initialization procedure.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
