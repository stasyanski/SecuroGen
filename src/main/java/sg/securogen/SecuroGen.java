package sg.securogen;

/**
 * App specific imports
 */
import sg.core.StageHelper;
import sg.core.WindowHelper;
import sg.windowHelpers.SecuroGenWindowHelper;

/**
 * JavaFX library
 */
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the main application class
 * responsible for setting up the application, loading the fxml and launching the application initially
 */
public class SecuroGen extends Application {
    private final WindowHelper windowHelper;

    /**
     * Securogen constructor, automatically run with the application specific settings
     * these are recommended settings for the app, including the width and height ratio being 16/10
     * the min height of the application and the min width, the max height and max width are also passed but never
     * used. Can be used by adding
     *
     *          "windowHelper.setMaxHeightWidth(stage);" in the start() method
     *
     */
    public SecuroGen() {
        this.windowHelper = new SecuroGenWindowHelper();
    }

    /**
     * The main entrypoint which is used to create the initial application
     * @param stage this is stage for this app passed to the start method
     */
    @Override
    public void start(Stage stage) {
        StageHelper.loadFXML(
                "/sg/SecuroGen/main-view.fxml",
                stage,
                this.windowHelper.getTitle(),
                this.windowHelper.getIconPath(),
                this.windowHelper.getWidth(),
                this.windowHelper.getHeight()
        );
        this.windowHelper.setMinHeightWidth(stage);
        this.windowHelper.setIcon(stage);
    }

    /**
     * The main method that launches the aforementioned made application / window
     * @param args standard command line args
     */
    public static void main(String[] args) {
        launch();
    }
}