package sg.windowHelpers;

/**
 * App specific imports
 */

import sg.constants.Constants;
import sg.core.WindowHelper;

/**
 * For separation of concerts, controlling the windows of each window
 * is done in specific classes. The icon path can be dinamically assigned here
 * or can be kept as the original icon path
 */
public class PopupControllerWindowHelper extends WindowHelper {
    public PopupControllerWindowHelper() {
        super(
                "Popup",
                Constants.ICON_PATH,
                300.0 / 150.0,
                150.0 / 300.0,
                150.0,
                300.0,
                150.0,
                300.0
        );
    }
}
