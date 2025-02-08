package sg.abstracts;

/**
 * App specific imports
 */

import sg.controllers.PopupController;
import sg.database.DatabaseConnection;
import sg.database.DatabaseTable;
import sg.interfaces.NewestControllerInterface;

/**
 * JavaSQL library imports
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract class for retrieving the newest value from the database
 * this class is used in multiple classes, uses in history controller and settings controller
 */
public abstract class NewestControllerAbstract implements NewestControllerInterface {
    /**
     * @return the value of the newest setting which either 1 or 0
     */
    @Override
    public int getNewestValue() {
        // this is a try-with-resources statement, used to terminate the resources once
        // the statement expires, making it close automatically
        try (Connection database = DatabaseConnection.getConnection()) {
            // The reason a connection is made as a local variable
            // is because it's important in SQLite and Java
            // to terminate the current open DB connection session
            // otherwise it will clash with other connection establishing
            // attempts and cause "DB is locked / busy" error
            DatabaseTable settingsTable = new DatabaseTable(database, "settings", "id");
            ResultSet row = settingsTable.findOne("id = 1");
            if (row.next()) {
                return row.getInt("newest");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error during getNewestValue " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during getNewestValue " + e.getMessage());
        }
        // if not fallback and simply return 0
        return 0;
    }
}
