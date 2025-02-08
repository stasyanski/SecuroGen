package sg.controllers;

/**
 * JavaFX Library imports
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * App specific imports
 */

import sg.core.StageHelper;
import sg.interfaces.ControllerInterface;

/**
 * This controller is a general controller for all the classes
 * inherited by 4 inheritors, it handles UI interactions and controls
 * the user interface / stage
 */
public class Controller implements ControllerInterface {
    /**
     * Used to control the stage, passed via setStage from loadFXML
     */
    protected Stage stage;

    /**
     * The main menu FXML sidebar buttons
     */
    @FXML
    private Button securoGen;
    @FXML
    private Button history;
    @FXML
    private Button settings;

    /**
     * Initileses the controller and sets up the setOnAction event listeners
     * !! IMPORTANT for the checks to be made null, causes errors otherwise
     */
    @FXML
    public void initialize() {
        if (this.securoGen != null) {
            this.securoGen.setOnAction(e -> onSecuroGenBtnClick());
        }
        if (this.history != null) {
            this.history.setOnAction(e -> onHistoryBtnClick());
        }
        if (this.settings != null) {
            this.settings.setOnAction(e -> onSettingsBtnClick());
        }
    }

    /**
     * Setter for the stage, used to pass stage to the controller
     * @param stage the stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * The functions below are used to load the respective specific
     * window when the sidebar buttons are pressed, passes the title of the current stage, the icon url
     * the stage itself (so the scene of current stage can be modified) and the fxml to load next
     */
    private void onSecuroGenBtnClick() {
        try {
            StageHelper.loadWindow(
                    "/sg/securogen/main-view.fxml",
                    this.stage,
                    this.stage.getTitle(),
                    this.stage.getIcons().getFirst().getUrl());
        } catch (Exception e) {
            System.err.println("Error during onSecuroBtnClick " + e.getMessage());
        }
    }
    private void onHistoryBtnClick() {
        try {
            StageHelper.loadWindow(
                    "/sg/securogen/history.fxml",
                    this.stage,
                    this.stage.getTitle(),
                    this.stage.getIcons().getFirst().getUrl());
        } catch (Exception e) {
            System.err.println("Error during onHistoryBtnClick " + e.getMessage());
        }
    }
    private void onSettingsBtnClick() {
        try {
            StageHelper.loadWindow(
                    "/sg/securogen/settings.fxml",
                    this.stage,
                    this.stage.getTitle(),
                    this.stage.getIcons().getFirst().getUrl());
        } catch (Exception e) {
            System.err.println("Error during onSettingsBtnClick " + e.getMessage());
        }
    }
}