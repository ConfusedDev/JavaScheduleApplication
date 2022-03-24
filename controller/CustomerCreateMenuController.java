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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controls the communication between the user interface for creating customers and the underlying data manipulations. */
public class CustomerCreateMenuController implements Initializable {

    Stage stage;
    Parent scene;

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

    @FXML
    private ComboBox<City> cityComboBox;

    @FXML
    private ComboBox<Country> countryComboBox;

    /** Activates the city combobox with results based on selected country.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionActivateCityComboBox(ActionEvent event){

        Country selectedCountry = countryComboBox.getValue();
        ObservableList<City> filteredCities = FXCollections.observableArrayList();
        ObservableList<City> allCities = CityDAO.getAllCities();
        allCities.stream()
                .filter(city -> city.getCountryId()==selectedCountry.getCountryId())
                .forEach(city -> filteredCities.add(city));
        cityComboBox.setDisable(false);
        cityComboBox.setItems(filteredCities);
    }

    /** Returns the user to the customer main menu.
    * @param event ActionEvent associated with the button being activated. */
    @FXML
    void onActionDisplayCustomerMenu(ActionEvent event) throws IOException {

        /** Returns to Customer Menu. */
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Checks to make sure required input has been entered and then creates a new customer record.
    * @param event ActionEvent associated with the button being activated. */
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

        /** Sends data to DAO for customer creation. */
        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String customerAddress2 = customerAddress2Txt.getText();
        String customerPostalCode = customerPostalCodeTxt.getText();
        String customerPhone = customerPhoneTxt.getText();
        City city = cityComboBox.getValue();

        CustomerDAO.createCustomer(customerName, customerAddress, customerAddress2, customerPostalCode, customerPhone,
                city.getCityId());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Populates the window with available city options to apply to the customer record.
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
