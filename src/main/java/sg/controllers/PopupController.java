package sg.controllers;

/**
 * JavaFX Library imports
 */

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Application specific imports
 */

import sg.core.StageHelper;
import sg.core.WindowHelper;
import sg.windowHelpers.PopupControllerWindowHelper;

/**
 * This class is a general class that can be used
 * to display error messages / or any other popup dialogue
 * handles all the logic
 */
public class PopupController extends Controller {
    /**
     * windowHelper stores an instance of window helper and the parameters passed to it
     * display text stores the setter value with the string.
     */
    private final WindowHelper windowHelper;
    private String displayText;
    /**
     * displayField FXML id which stores the appended display text to it
     */
    @FXML
    private TextArea displayField;

    /**
     * The constructor initialises a new instance of the window helper specific to popup controller
     * with the specific parameters setup in those window Helpers
     */
    public PopupController() {
        this.windowHelper = new PopupControllerWindowHelper();
    }

    /**
     * initiliases the parent class
     * constructor, to have access to variables
     */
    @FXML
    public void initialize() {
        super.initialize();
    }

    /**
     * Setter used to set the text to be displayed
     * @param displayText the text to be set
     */
    private void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    /**
     * Method to append the display text to class level variable
     */
    private void appendDisplayText() {
        if(this.displayText!=null) {
            this.displayField.setText(this.displayText);
        }
    }

    /**
     * This FXML method is used to create create the dialogue window
     * loads the dialogue fxml and uses the popup controller with the userdata
     *
     */
    @FXML
    private void showDialogue() {
        try {
            Stage stage = new Stage();
            StageHelper.loadFXML(
                    "/sg/securogen/dialogue.fxml",
                    stage,
                    this.windowHelper.getTitle(),
                    this.windowHelper.getIconPath(),
                    this.windowHelper.getWidth(),
                    this.windowHelper.getHeight());
            this.windowHelper.setMaxHeightWidth(stage);
            this.windowHelper.setMinHeightWidth(stage);

            // this line is very important and it retrieves the controller instance
            // thats related to the newly created scene
            PopupController controller = (PopupController) stage.getScene().getUserData();
            controller.setDisplayText(this.displayText);
            controller.appendDisplayText();

            stage.show();
        } catch (Exception e) {
            // prints the error to system
            // cant use showErrorPopup for obvious reasons
            // so printing it instead
            System.err.println("Error during showDialogue " + e.getMessage());
        }
    }

    /**
     * Used to automaticaly instantiate an instance of popup Controller,
     * set the text, and automatically show the dialogue,
     * prevents a lot of repetition in try catch blocks
     * @param message to be displayed
     */
    public static void showErrorPopup(String message) {
        PopupController popupController = new PopupController();
        popupController.setDisplayText(message);
        popupController.showDialogue();
    }
}
