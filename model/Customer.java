package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Provides the data model for the customers used by the application and instantiates them. */
public class Customer {

    private int customerId;
    private String customerName;
    private int addressId;
    private String address;
    private String address2;
    private String city;
    private String country;
    private String postalCode;
    private String phone;
    private int active;
    private LocalDate createDate;
    private LocalTime createTime;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    /** Instantiates a new customer object using the provided parameters.
     * @param country String object containing the country's name.
     * @param active Integer value storing whether the customer is active or not.
     * @param address String object containing the customer's first line of address information.
     * @param address2 String object containing the customer's second line of address information if needed.
     * @param addressId Integer value of the address id in the database associated with the customer.
     * @param city String object storing the value of the city name associated with the customer.
     * @param createDate LocalDate object containing the value of the date the customer was created.
     * @param createdBy String object containing the name of the user associated with creating the customer.
     * @param customerId Integer value storing the associated customer's id.
     * @param createTime LocalTime containing the time the customer object was created.
     * @param customerName String object containing the customer's name.
     * @param lastUpdate LocalDateTime object containing the date and time of last update.
     * @param lastUpdateBy String object containing the name of the last user to update the record.
     * @param phone String object storing the customers phone number.
     * @param postalCode String object storing the customer's postal code. */
    public Customer(int customerId, String customerName, int addressId, String address, String address2, String city, String country,
                    String postalCode, String phone, int active, LocalDate createDate, LocalTime createTime,
                    String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.active = active;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /** Returns the customer id.
     * @return Integer value of the customer's id. */
    public int getCustomerId() {
        return customerId;
    }

    /** Returns the customer name string.
     * @return String object containing the customer's name. */
    public String getCustomerName() {
        return customerName;
    }

    /** Returns the customer address.
     * @return String object containing the customer's address. */
    public String getAddress() {
        return address;
    }

    /** Returns the customer address2 string.
     * @return String object containing the second line of the customer's address information. */
    public String getAddress2() {
        return address2;
    }

    /** Returns the customer city.
     * @return String object containing the name of the city associated with the customer record. */
    public String getCity() {
        return city;
    }

    /** Returns the customers postal code.
     * @return String object containing the customer's postal code. */
    public String getPostalCode() {
        return postalCode;
    }

    /** Returns the customers phone number string.
     * @return String object containing the customer's phone number. */
    public String getPhone() {
        return phone;
    }

    /** Returns the customers addressid field.
     * @return Integer value of the address id associated with the customer record. */
    public int getAddressId() {
        return addressId;
    }

    /** Returns the createdby string of the customer record.
     * @return String object containing the name of the user that created the record. */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Returns the country's name string associated with the customer record.
     * @return String object containing the country's name associated with the customer. */
    public String getCountry() {
        return country;
    }

    /** Overrides the default implementation of toString to return the customers name string.
     * @return String object containing the customer's name. */
    @Override
    public String toString(){
        return customerName;
    }
}
