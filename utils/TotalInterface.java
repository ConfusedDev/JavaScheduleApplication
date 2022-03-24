package utils;

import javafx.collections.ObservableList;
import model.Customer;

/** Provides an interface to be used for report generation by the calendar controller. */
public interface TotalInterface {
    /**
     *
     * @param o ObservableList object to be operated on by the interface.
     * @return Integer value of the total calculated.
     */
    int total(ObservableList<Customer> o);
}

