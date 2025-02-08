package sg.interfaces;

import javafx.stage.Stage;

/**
 * Defines the methods that should be in window helper class
 * for detailed Javadocs, please check the abstract window helper class
 */
public interface WindowHelperInterface {
    void setMinHeightWidth(Stage stage);
    void setMaxHeightWidth(Stage stage);
    double getWidth();
    double getHeight();
    String getTitle();
    String getIconPath();
    void setIcon(Stage stage);
}
