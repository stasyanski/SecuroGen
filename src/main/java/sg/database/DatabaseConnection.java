package sg.database;

/**
 * App specific imports
 */

import sg.constants.Constants;

/**
 * JavaSQL library
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The database connection class aims to provide
 * a simple way to connect to the SQLite database, to store
 */
public class DatabaseConnection {
    /**
     * Main method for setting and testing the database connection
     * @param args default command line arguments
     *
     * [in code reference]
     * SQLite Tutorial (n.d.). Connect to the SQLite Database using SQLite JDBC Driver. [online] Available from:
     * @see https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
     */
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(Constants.DB_SQLITE_PATH)) {
            if (!conn.isClosed()) {
                System.out.println("Database connection established");
            }
        } catch (SQLException e) {
            System.err.println("Error during main method of DatabaseConnection " + e.getMessage());
        }
    }

    /**
     * This method is used to make a connection with the database and return the connection
     * otherwise it will return null
     *
     * @return the database connection to be used in other methods across any other class
     * @throws SQLException if a database access error occur
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(Constants.DB_SQLITE_PATH);
        } catch (SQLException e) {
            System.err.println("Error during getConnection " + e.getMessage());
        }
        return null;
    }
}
