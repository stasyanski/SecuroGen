package sg.core;

/**
 * JavaFX library
 */

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * App specific imports
 */

import sg.interfaces.WindowHelperInterface;

/**
 * The window helper class helps in setting the parameters for a stage
 * to customise the creation of a stage, including common parameters like min height and min width
 */
public class WindowHelper implements WindowHelperInterface {
    private final String title;
    private final String iconPath;
    private final double width;
    private final double height;
    private final double minHeight;
    private final double minWidth;
    private final double maxHeight;
    private final double maxWidth;

    /**
     * Description of the parameters, all these parameters must be implemented for any window!
     * @param title the title of the window itself
     * @param iconPath the iconPath image to be set in the top left
     * @param widthRatio the widthRatio, for example 16/10
     * @param heightRatio the height ratio, for example 10/16
     * @param minHeight the minimum height a window is allowed to be
     * @param minWidth the minimum width a window is allowed to be
     * @param maxHeight the max height
     * @param maxWidth the max width
     */
    public WindowHelper(String title, String iconPath, double widthRatio, double heightRatio, double minHeight, double minWidth, double maxHeight, double maxWidth) {
        this.title = title;
        this.iconPath = iconPath;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;

        // automatically getting the screen bounds of the user,to dinamically set the ratio of the application, for easy scalability
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();

        // this will make the app either to be 33% of the users screen, or make it minheight and min width
        this.width = Math.max(screenWidth * 0.33, minWidth);
        this.height = this.width * heightRatio;
    }

    /**
     * Takes in the icon path and sets the icon
     * @param stage the current stge
     */
    public void setIcon(Stage stage) {
        stage.getIcons().add(new Image(this.iconPath));
    }

    /**
     * Takes in the current stage and sets the min height and width
     * therefore min height and width is optional
     * @param stage the current stage
     */
    public void setMinHeightWidth(Stage stage) {
        stage.setMinHeight(this.minHeight);
        stage.setMinWidth(this.minWidth);
    }

    /**
     * Takes in the current stage and sets the min height and width
     * therefore min height and width is optional
     * @param stage the current stage
     */
    public void setMaxHeightWidth(Stage stage) {
        stage.setMaxHeight(this.maxHeight);
        stage.setMaxWidth(this.maxWidth);
    }

    /**
     * Getter for the stage width
     * @return width returns the current width of the stage
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Getter for the stage height
     * @return width returns the current width of the stage
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Getter for the title
     * @return returns the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for the icon path
     * @return the icon path
     */
    public String getIconPath() {
        return this.iconPath;
    }
}




