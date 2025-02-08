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
public class SecuroGenWindowHelper extends WindowHelper {
    public SecuroGenWindowHelper() {
        super(
                "SecuroGen",
                Constants.ICON_PATH,
                16.0 / 10.0,
                10.0 / 16.0,
                450,
                650,
                450,
                650
        );
    }
}
