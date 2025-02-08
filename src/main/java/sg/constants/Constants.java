package sg.constants;

/**
 * For separation of concerts, this constants package
 * stores constant variables that rarely change, and are used
 * throughout the code to avoid repetition
 */
public class Constants {
    // contains a (non relative) path to the icon path used for window helper subclasses.
    public static final String ICON_PATH = System.getProperty("user.dir") + "/src/main/resources/sg/securogen/securogen.png";
    // contains a relative path to the DB_SQLITE_PATH path
    public static final String DB_SQLITE_PATH = "jdbc:sqlite:sg.db";
    //the alphanumeric regex to check that all values are alphanumeric only
    /**
     * [in code reference]
     * bundyxc. (2009). Alphanumeric plus hyphens, periods, and underscores. [online] PHPFreaks. Available from:
     * @see https://forums.phpfreaks.com/topic/169452-solved-alphanumeric-plus-hypens-periods-and-underscores/#findComment-894083
     */
    public static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9_\\.\\-]*$";

    // constants used in securogen controller
    public static final String ALL_LETTERS = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    public static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMBERS = "0123456789";
    /**
     * [in code reference]
     * Krawczyk, P. [n.d.]. Password Special Characters [online] owasp.org. Available from:
     * @see https://owasp.org/www-community/password-special-characters
     */
    public static final String SPECIAL_CHARS = "!#$%&’()*+,-./:;<=>?@[\\]^_`{|}~";
}
