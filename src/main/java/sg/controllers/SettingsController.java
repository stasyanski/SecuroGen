package sg.controllers;

/**
 * JavaFX specific imports
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * Application specific imports
 */

import sg.abstracts.NewestControllerAbstract;
import sg.database.DatabaseConnection;
import sg.database.DatabaseTable;

/**
 * Java SQL library imports
 */
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Controller class for handling the logic of
 * the settings page / screen
 *
 */
public class SettingsController extends Controller {
    /**
     * FXML buttons with the corresponding fxid
     */
    @FXML
    private Button deletePasswordHistory;
    @FXML
    private CheckBox sortByNewest;

    /**
     * initialises the controller and the fxml
     */
    @FXML
    @Override
    public void initialize() {
        /**
         * initiliases the parent class
         * constructor, to have access to variables
         */
        super.initialize();
        /**
         * Runs the newest checkbox value method, which sets the checkbox isChecked to
         * true or false depending on the application settings in the database
         */
        newestCheckBoxValue();
        /**
         * run the delete database history when pressing the button
         */
        deletePasswordHistory.setOnAction(
                e -> deleteDbHistory()
        );
        /**
         * run the newest check box controller method, passing the current state of
         * the sort by newest check box
         */
        sortByNewest.setOnAction(
                e -> newestCheckBoxControl(sortByNewest.isSelected()
                ));
    }

    /**
     * This method is used to delete db history
     * it will clear all recently generated passwords
     */
    private void deleteDbHistory() {
        // This is a try-with-resources statement, used to terminate the resources once
        // the statement expires, making it close automatically
        try (Connection database = DatabaseConnection.getConnection()) {
            // the reason a connection is made as a local variable
            // is because its important in sqlite and java
            // to terminate current open db connection session
            // otherwise it will clash with other connection establishing
            // attempts and cause "DB is locked / busy" error
            DatabaseTable passwordTable = new DatabaseTable(database, "passwords", "id");
            passwordTable.deleteAllRows();
            // show feedback to user that the password history is deleted successfully
            PopupController.showErrorPopup("Password history deleted successfully.");
        } catch (SQLException e) {
            // if theres an sql exception
            System.err.println("SQL Error during ndeleteDbHistory " + e.getMessage());
        } catch (Exception e) {
            // otherwisethe general error
            System.err.println("Error during deleteDbHistory " + e.getMessage());
        }
    }

    /**
     * this method is used to retrieve the newest checkbox value
     * from the application settings table, in order for
     * consistency
     */
    private void newestCheckBoxValue() {
        try {
            // Create an instance of NewestControllerAbstract to use its getNewestValue method
            NewestControllerAbstract newestController = new NewestControllerAbstract() {};
            // Retrieve the newest value using the getNewestValue method
            int newestValue = newestController.getNewestValue();
            // set it as true or false depending if newest value evaluates to 1 or not 1
            sortByNewest.setSelected(newestValue == 1);
        } catch (Exception e) {
            System.err.println("Error during newestCheckBoxValue " + e.getMessage());
        }
    }
    /**
     * updates the sort by newest setting in the database on newest check box control
     * button pressed
     * @param isChecked whether the checkbox is checked or not
     */
    private void newestCheckBoxControl(boolean isChecked) {
        int newestValue;
        if (isChecked) {
            newestValue=1;
        } else {
            newestValue=0;
        }
        // This is a try-with-resources statement, used to terminate the resources once
        // the statement expires, making it close automatically
        try (Connection database = DatabaseConnection.getConnection()) {
            // The reason a connection is made as a local variable
            // is because it's important in SQLite and Java
            // to terminate the current open DB connection session
            // otherwise it will clash with other connection establishing
            // attempts and cause "DB is locked / busy" error
            DatabaseTable settingsTable = new DatabaseTable(database, "settings", "id");
            // update the settings table, passing the field, the id to restrict by, and the int as a string
            settingsTable.update("newest", 1, String.valueOf(newestValue));
        } catch (SQLException e) {
            // if theres an sql exception
            System.err.println("SQL Error during newestCheckBoxControl " + e.getMessage());
        } catch (Exception e) {
            // otherwise the general error
            System.err.println("Error during newestCheckBoxControl " + e.getMessage());
        }
    }
}



