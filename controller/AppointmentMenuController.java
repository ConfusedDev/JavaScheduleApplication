package controller;

import database.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for appointments and the underlying data manipulations. */
public class AppointmentMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    /** Opens up a window allowing the user to schedule a new appointment.
     * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayCreateAppointmentMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CreateAppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Opens up a window to edit a selected appointment and passes the information about that appointment to its controller.
     * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayEditAppointmentMenu(ActionEvent event) throws IOException {

        /** Populates information into Appointment Edit Menu. */
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/EditAppointmentMenu.fxml"));
        fxmlLoader.load();

        EditAppointmentMenuController eamController = fxmlLoader.getController();
        eamController.receiveAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Deletes the currently selected appointment record from the table.
     * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Appointment: "+
                appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentId()+
                " for a(n) " + appointmentTableView.getSelectionModel().getSelectedItem().getType()+
                " has been deleted.");
        alert.show();

        AppointmentDAO.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentId());
        appointmentTableView.setItems(AppointmentDAO.getAllAppointments());
    }

    /** Returns the user to the main menu.
     * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Fills in the window with a table of all appointments.
     * @param url URL object to be used by the initialization operation.
     * @param rb ResourceBundle to be used by the initialization operation. */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        /** Populates table with appointment data. */
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        appointmentTableView.setItems(allAppointments);
        appointmentCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

}
