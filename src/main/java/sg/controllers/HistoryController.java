package sg.controllers;

/**
 * JavaFX specific imports
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller class for handling the logic of the
 * history page and screen
 */
public class HistoryController extends Controller {
    /**
     * FXML buttons with the corresponding fxid
     */
    @FXML
    private ScrollPane passwordContainer;

    /**
     * Instance of NewestControllerAbstract to delegate the getNewestValue call.
     */
    private final NewestControllerAbstract newestController;

    /**
     * Constructor for HistoryController.
     * Initializes the NewestControllerAbstract instance.
     */
    public HistoryController() {
        this.newestController = new NewestControllerAbstract() {};
    }

    /**
     * Initializes the controller and
     * sets the display of password history
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
         * used to load the password
         * history from a db
         * into a loaded fxml
         * for each db entry / password
         */
        displayPasswordHistory();
    }

    /**
     * this method is used to retrieve all the passwords from the password table
     * and sort by the order in getNewestValue
     */
    private void displayPasswordHistory() {
        // this is a try-with-resources statement, used to terminate the resources once
        // the statement expires, making it close automatically
        try (Connection database = DatabaseConnection.getConnection()) {
            // the reason a connection is made as a local variable
            // is because it's important in SQLite and Java
            // to terminate the current open DB connection session
            // otherwise it will clash with other connection establishing
            // attempts and cause "DB is locked / busy" error
            DatabaseTable passwordTable = new DatabaseTable(database, "passwords", "id");

            // get the sorting order needed for the history, either desc or asc
            String sortOrder = getNewestValue();
            // retrieve all the passwords
            ResultSet allPasswords = passwordTable.findAll(null, "date", sortOrder);

            /**
             * The code below contains some dynamic fxml
             */
            // create a dynamic container to hold all the passwords, which will be appended
            VBox dynamicVBox = new VBox();
            // process the result set and add entries to the VBox
            while (allPasswords.next()) {
                // store the password
                String password = allPasswords.getString("password");
                //store the date
                String date = allPasswords.getString("date");
                // load the fxml and set it as password entry
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sg/securogen/displayPassword.fxml"));
                VBox passwordEntry = fxmlLoader.load();

                // lookup the fields from the fxmlloader password entry
                TextField passwordField = (TextField) passwordEntry.lookup("#genPasswordFromDb");
                TextField dateField = (TextField) passwordEntry.lookup("#genPasswordFromDbDate");

                // set the text for each field
                passwordField.setText(password);
                dateField.setText(date);

                // add the password entry to
                dynamicVBox.getChildren().add(passwordEntry);
            }
            // append the values to the scrollpane from the dynamic vbox
            this.passwordContainer.setContent(dynamicVBox);
        } catch (SQLException e) {
            System.err.println("SQL Error during displayPasswordHistory " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during displayPasswordHistory " + e.getMessage());
        }
    }

    /**
     * Retrieves the sorting order for the password history based
     * on the getNewestValue method, if its 1 it returns desc if its not 1
     * returns asc
     *
     * @return the settings bassed on newestValue
     */
    private String getNewestValue() {
        int newestValue = newestController.getNewestValue();
        if (newestValue == 1) {
            return "DESC";
        } else {
            return "ASC";
        }
    }
}





