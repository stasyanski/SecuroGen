package sg.controllers;

/**
 * JavaFX Library imports
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * App specific imports
 */
import sg.core.InputValidation;
import sg.database.DatabaseConnection;
import sg.database.DatabaseTable;
import sg.constants.Constants;

/**
 * JavaSQL library imports
 */
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Java standard library imports
 */
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This controller handles the logic for password generation
 * and all else on the SecuroGen page
 */
public class SecuroGenController extends Controller {
    /**
     * Instantiating the required classes to perform input validation and random secure
     * password generation
     */
    private static final SecureRandom random = new SecureRandom();
    // this is hardcoded but i couldnt find a better way
    private final String[] checks = new String[3];

    /**
     * FXML components with the corresponding fxid
     */
    @FXML
    private CheckBox useUpperCheck;
    @FXML
    private CheckBox useSymbolsCheck;
    @FXML
    private CheckBox useNumbersCheck;
    @FXML
    private Label passwordLengthLabel;
    @FXML
    private Slider passwordLengthSlider;
    @FXML
    private TextField phraseToInclude;
    @FXML
    private Button generatePasswordBtn;
    @FXML
    private TextField generatedPasswordDisplay;

    /**
     * Initializes the controller and sets up event listeners
     */
    @FXML
    @Override
    public void initialize() {
        /**
         * Initializes the parent class
         * constructor, to have access to variables
         */
        super.initialize();
        /**
         * Initializes the slider with the default value
         * of the password length slider set in the FXML
         * properties
         */
        updateLengthLabel((int) this.passwordLengthSlider.getValue());
        /**
         * Promote to int the slider value using type casting
         * and set on action when press generate password btn
         * to retrieve the values for user preferences
         */
        this.generatePasswordBtn.setOnAction(event -> {
            generatePassword(
                    (int) this.passwordLengthSlider.getValue(),
                    this.useUpperCheck.isSelected(),
                    this.useNumbersCheck.isSelected(),
                    this.useSymbolsCheck.isSelected(),
                    this.phraseToInclude.getText()
            );
        });
        /**
         * Updates the length label with new value int value
         * for dynamic updating to show visual feedback
         */
        passwordLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateLengthLabel(newValue.intValue());
        });
    }

    /**
     * Generates a password based on the provided preferences
     * that was gathered by the set on action
     * @param length the length of the password that's desired
     * @param upper whether to include uppercase letters for customizability
     * @param number whether to include numbers for customizability
     * @param symbols option to include symbols for customizability
     * @param phrase a phrase to include in the password for customizability
     */
    private void generatePassword(int length, boolean upper, boolean number, boolean symbols, String phrase) {
        try {
            // checks if the phrase is not null, and the phrase is not empty
            // also checks if the checkphrase returned true or false
            // to check whether it passed all checks
            // if it did not pass checks, then end password generation prematurely
            if (phrase != null && !phrase.isEmpty() && !checkPhrase(phrase)) {
                return;
            }
            // holds the parameters / character pool from which to generate a password from
            String charPool = "";

            // if statement to add to charPool based on preferences
            if (upper) {
                charPool += Constants.ALL_LETTERS;
            } else {
                charPool += Constants.LOWERCASE_LETTERS;
            }
            if (number) {
                charPool += Constants.NUMBERS;
            }
            if (symbols) {
                charPool += Constants.SPECIAL_CHARS;
            }

            // a char array to hold the generated password characters
            char[] password = new char[length - phrase.length() - 1];

            // fill the password array with random characters from charpool
            for (int i = 0; i < password.length; i++) {
                /**
                 * [in code reference]
                 * GeeksForGeeks. (2017). Random vs Secure Random numbers in Java. [online] Available from:
                 * @see https://www.geeksforgeeks.org/random-vs-secure-random-numbers-java/
                 */
                int randomIndex = random.nextInt(charPool.length());
                password[i] = charPool.charAt(randomIndex);
            }

            // convert the password to string and store as final password
            String finalPassword = new String(password);

            // if the user phrase is not empty, then append it at the beginning, concatenated with an underscore.
            if (!phrase.isEmpty()) {
                finalPassword = phrase + "_" + finalPassword;
            }

            // set the FXML id of genpassword display, append the final password to it
            generatedPasswordDisplay.setText(finalPassword);
            // insert the gen password into the password history db
            insertGenPassword(finalPassword);
        } catch (Exception e) {
            System.err.println("Error during generatePassword " + e.getMessage());
        }
    }

    /**
     * Checks the input for validation with the provided phrase
     * @param phrase the phrase to be checked
     * @return true if the check is valid, false if not, and returns the checks
     */
    private boolean checkPhrase(String phrase) {
        try {
            int checkIndex = 0;
            String check = InputValidation.checkSpace("Phrase", phrase);
            if (check != null) checks[checkIndex++] = check;

            check = InputValidation.checkAlphaNumeric("Phrase", phrase);
            if (check != null) checks[checkIndex++] = check;

            check = InputValidation.checkLength("Phrase", phrase, 0, 32);
            if (check != null) checks[checkIndex++] = check;
            if (checkIndex > 0) {
                showDialog(checkIndex);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error during checkPhrase " + e.getMessage());
        }
        return false;
    }

    /**
     * Inserts the generated password into the database
     * so that it can be later redisplayed for password gen history
     * @param password the password to insert as string
     */
    private void insertGenPassword(String password) {
        // try with insert so connection can be terminated asfter
        /**
         * [in code reference]
         * Jonas. (2011). How should I use try-with-resouces with JDBC? [online] StackOverflow.com. Available from:
         * @see https://stackoverflow.com/questions/8066501/how-should-i-use-try-with-resources-with-jdbc
         */
        try (Connection connection = DatabaseConnection.getConnection()) {
            DatabaseTable passwordTable = new DatabaseTable(connection, "passwords", "id");

            // simpledate format with the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentDateTime = sdf.format(new Date());

            // string array with field and value
            String[] fieldsAndValues = new String[]{
                    "password", password,
                    "date", currentDateTime
            };
            passwordTable.insert(fieldsAndValues);
        } catch (SQLException e) {
            System.err.println("SQL Error during insertGenPassword " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during insertGenPassword " + e.getMessage());
        }
    }

    /**
     * Simple method to update the password length label with the current slider value
     * @param length the length to display in int
     */
    private void updateLengthLabel(int length) {
        passwordLengthLabel.setText("Password Length: " + length);
    }

    /**
     * Displays a dialog with validation messages
     * @param checkCount the number of validation messages to display
     */
    private void showDialog(int checkCount) {
        try {
            String validationMessage = "";
            for (int i = 0; i < checkCount; i++) {
                if (checks[i] != null) {
                    // newline after each message
                    validationMessage += checks[i] + "\n";
                }
            }
            PopupController.showErrorPopup(validationMessage);
        } catch (Exception e) {
            System.err.println("Error during showDialog " + e.getMessage());
        }
    }
}
