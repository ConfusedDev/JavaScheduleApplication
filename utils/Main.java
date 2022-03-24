package utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

/** Main entry point for the javafx application. Also opens and closes sql connection. */
public class Main extends Application {

    /** Starts the javafx application with the login menu view.
     * @param primaryStage Stage object for the main stage of the application. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginMenu.fxml"));
        primaryStage.setTitle("Schedule Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /** Main entry point for a java application.
     * @param args Array object containing command line arguments as String objects.
     * @throws SQLException Exception thrown based on invalid sql operations being attempted by the connection. */
    public static void main(String[] args) throws SQLException {

        DatabaseConnection.startConnection();
        launch(args);
        DatabaseConnection.closeConnection();
    }
}
