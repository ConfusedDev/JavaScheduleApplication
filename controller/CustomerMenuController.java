package controller;

import database.CustomerDAO;
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
import model.Country;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for customers and the underlying data manipulations. */
public class CustomerMenuController implements Initializable{

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, Country> customerCountryCol;

    @FXML
    private TableColumn<?, ?> customerCityCol;

    @FXML
    private TableColumn<?, ?> customerAddress2Col;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;



    @FXML
    private TableView<Customer> customerTableView;

    /** Opens a window to allow the user to create a new customer record.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayCreateCustomerMenu(ActionEvent event) throws IOException {

        /** Opens customer creation window. */
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerCreateMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Deletes the currently selected customer record from the database.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        /** Sends customer id to DAO for deletion. */
        int customerId = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
        int addressId = customerTableView.getSelectionModel().getSelectedItem().getAddressId();
        CustomerDAO.deleteCustomer(customerId, addressId);
        customerTableView.setItems(CustomerDAO.getAllCustomers());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Customer: "+customerId+" has been successfully removed.");
        alert.show();
    }

    /**Opens a customer edit window and fills in the data on the selected customer.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionEditCustomer(ActionEvent event) throws IOException {

        /** Populates information into Customer Edit Menu. */
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/CustomerEditMenu.fxml"));
        fxmlLoader.load();

        CustomerEditMenuController cemController = fxmlLoader.getController();

        Customer toEdit = customerTableView.getSelectionModel().getSelectedItem();
        cemController.receiveCustomer(toEdit);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Returns the user to the main menu to make a new selection.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        /** Returns to Main Menu. */
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Populates the tableview with a collection of all customer records.
     *
     * @param url URL to be used by the initialization procedure.
     * @param rb ResourceBundle to be used by the initialization procedure.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        /** Adds initial table values. */
        ObservableList<Customer> allCustomers = CustomerDAO.getAllCustomers();
        customerTableView.setItems(allCustomers);
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerAddress2Col.setCellValueFactory(new PropertyValueFactory<>("address2"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }

}
