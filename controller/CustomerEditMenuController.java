package controller;

import database.CityDAO;
import database.CountryDAO;
import database.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.City;
import model.Country;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for editing a customer and the underlying data manipulations. */
public class CustomerEditMenuController implements Initializable {

    Stage stage;
    Parent scene;
    private Customer customerToEdit;


    @FXML
    private ComboBox<City> cityComboBox;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private TextField customerPostalCodeTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerPhoneTxt;

    @FXML
    private TextField customerAddress2Txt;

    /**Checks the required fields for a new customer and then edits the currently selected customer record.
    * @param event ActionEvent associated with the button being activated.
     *
     *
     * Sends customer data to DAO for update query.
     * */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {

        /** Makes sure required fields are filled. */
        if (customerNameTxt.getText().isEmpty() || customerAddressTxt.getText().isEmpty() ||
                customerPhoneTxt.getText().isEmpty() || customerPostalCodeTxt.getText().isEmpty() ||
                cityComboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Missing required fields");
            alert.show();
            return;
        }

        int customerId = customerToEdit.getCustomerId();
        int addressId = customerToEdit.getAddressId();
        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String customerAddress2 = customerAddress2Txt.getText();
        String customerPostalCode = customerPostalCodeTxt.getText();
        String customerPhone = customerPhoneTxt.getText();
        City city = cityComboBox.getValue();

        CustomerDAO.modifyCustomer(customerId, addressId, customerName, customerAddress, customerAddress2,
                customerPostalCode, customerPhone, city.getCityId());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Filters the city combobox with results based on selected country.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionActivateCityComboBox(ActionEvent event){

        Country selectedCountry = countryComboBox.getValue();
        ObservableList<City> filteredCities = FXCollections.observableArrayList();
        ObservableList<City> allCities = CityDAO.getAllCities();
        allCities.stream()
                .filter(city -> city.getCountryId()==selectedCountry.getCountryId())
                .forEach(city -> filteredCities.add(city));
        cityComboBox.setItems(filteredCities);
    }

    /** Returns the user to the main customer menu.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayCustomerMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Populates the window with information about the currently selected customer to edit.
     * @param customer Customer object to populate data into the form. */
    public void receiveCustomer(Customer customer){

        /** Initializes customer values passed from customer menu. */

        customerToEdit = customer;

        /**Selects the correct country record and fills the city combobox. */
        if(customer.getCountry().equalsIgnoreCase("United Kingdom")){
            countryComboBox.getSelectionModel().select(0);
        } else countryComboBox.getSelectionModel().select(1);

        /** Populates the city combobox and selects correct item in combobox. */
        cityComboBox.setDisable(false);
        ObservableList<City> filteredCities = FXCollections.observableArrayList();
        ObservableList<City> allCities = CityDAO.getAllCities();
        allCities.stream()
                .filter(city -> city.getCountryId()==countryComboBox.getSelectionModel().getSelectedItem().getCountryId())
                .forEach(city -> filteredCities.add(city));
        cityComboBox.setItems(filteredCities);
        for(City c : filteredCities){
            if(customer.getCity().equalsIgnoreCase(c.toString())){
                cityComboBox.getSelectionModel().select(c);
            }
        }

        /** Fills in the rest of the customer information. */
        customerAddressTxt.setText(customer.getAddress());
        customerAddress2Txt.setText(customer.getAddress2());
        customerNameTxt.setText(customer.getCustomerName());
        customerPhoneTxt.setText(customer.getPhone());
        customerPostalCodeTxt.setText(customer.getPostalCode());

    }

    /** Populates the city combobox with options for cities to apply to the customer record.
     *
     * @param url URL to be used by the initialization procedure.
     * @param rb ResourceBundle to be used by the initialization procedure.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        /** Adds country choices to combobox. */
        countryComboBox.setItems(CountryDAO.getAllCountries());

    }
}
