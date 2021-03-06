package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Encapsulates the connection generated by the jdbc api and provides a reference throughout the application. */
public class DatabaseConnection {


    private static final String url = "URL";
    private static final String username = "USERNAME";
    private static final String password = "PASSWORD";
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    /** Returns a connection object using the jdbc api to generate a connection to the database.
     * @return Connection object containing connection to the database. */
    public static Connection startConnection(){
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /** Ends the current connection to the database. */
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection Closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Returns a reference to the open connection to the database.
     * @return Connection object reference to the open connection to the database. */
    public static Connection getConn(){
        return conn;
    }
}
