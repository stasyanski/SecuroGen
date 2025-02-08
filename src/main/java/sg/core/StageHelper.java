package sg.core;

/**
 * JavaFX library imports
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App specific imports
 */

import sg.controllers.Controller;

/**
 * This class contains the stage helper logic which loads the FXML and sets the specific characteristics
 * of a window, also creating the scene to store the FXML inside.
 */
public class StageHelper {
    /**
     * This method loads the fxml files and sets the specific parameters
     * for the window, including title, iconpath, sizes etc
     * it also acts as a "router" passing the stage to the main controller class
     * @param fxmlPath the path to the fxml file to be loaded
     * @param stage the stage to be set up
     * @param title the title of the window for the stage
     * @param iconPath the path to the window icon
     * @param width the width of the window thats being created
     * @param height the height of the window thats being created
     */
    public static void loadFXML(String fxmlPath, Stage stage, String title, String iconPath, double width, double height) {
        try {
            // create an fxml instance to be used for loaidng into the scene
            FXMLLoader fxmlLoader = new FXMLLoader(StageHelper.class.getResource(fxmlPath));

            // load the fxml file into the scene, alognside the width and height
            Scene scene = new Scene(fxmlLoader.load(), width, height);

            // get the FXML files controller (specified in the fx:controller values in the fxml files)
            Controller controller = fxmlLoader.getController();

            // set the stage into the specific controller
            controller.setStage(stage);

            // store the controller in the  in the scene, useful for the PopupController!
            scene.setUserData(controller);

            stage.setTitle(title);
            stage.getIcons().add(new javafx.scene.image.Image(iconPath));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error during loadFXML " + e.getMessage());
        }
    }

    /**
     * Before loaidng the fxml file, this loadWindow ensures that switching between windows
     * is seamless in terms of window height and width, as well as retaining the title and iconpath
     *
     * @param fxmlPath the path to the FXML file to eb loaded
     * @param stage the stage to be set up
     * @param title the title of the window to be opened
     * @param iconPath the path to the window icon to be inserted into window
     */
    public static void loadWindow(String fxmlPath, Stage stage, String title, String iconPath) {
        try {
            double currentWidth;
            double currentHeight;

            // check if the stage already has a scene
            if (stage.getScene() != null) {
                // if it does then get the current scene width and height
                // THIS IS VERY USEFUL, if just using get width, everytime a scene is switched
                // the current width and height will increase slightly, as the stage width and height
                // is counted with the borders of the app, and the context menu at the top!
                currentWidth = stage.getScene().getWidth();
                currentHeight = stage.getScene().getHeight();
            } else {
                // if there isnt a scene, then its safe to just get the width of the stage
                currentWidth = stage.getWidth();
                currentHeight = stage.getHeight();
            }

            // after all of this is ensured, the fxml file can be loaded
            loadFXML(fxmlPath, stage, title, iconPath, currentWidth, currentHeight);
        } catch (Exception e) {
            System.err.println("Error during loadWindow " + e.getMessage());
        }
    }
}
