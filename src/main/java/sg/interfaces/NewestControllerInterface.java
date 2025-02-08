package sg.interfaces;

/**
 * Interface for retrieving the newest value
 * from the db for the application preferences
 */
public interface NewestControllerInterface {
    /**
     * @return the value of the "newest" setting (1 or 0)
     */
    int getNewestValue();
}

