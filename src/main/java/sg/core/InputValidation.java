package sg.core;

import sg.constants.Constants;

/**
 * This class is used to handle input validation into the controllers
 * it is used to ensure input matches the standards needed
 * it is reusable, and returns an error message which can be printed via a dialogue
 */
public class InputValidation {

    /**
     * Checklength method checks if a value is the desired length
     * @param field the field that is being tested, this is just the field thats being inputted into. e.g. password Field
     * @param value the value that is actually being inputted
     * @param minLen the min length possible
     * @param maxLen the max length possible
     * @return the error message concat as str or null
     */
    public static String checkLength(String field, String value, int minLen, int maxLen) {
        int length = value.length();
        if (length < minLen || length > maxLen) {
            return field + ": " + value + " is not between " + minLen + " and " + maxLen + " characters.";
        }
        return null;
    }

    /**
     * The checkAlphaNumeric method checks if the text contains only alphanumeric values
     * based on regex, using value matches.
     * @param field the field being tested
     * @param value the actual value
     * @return the error message concat as str or null
     */
    public static String checkAlphaNumeric(String field, String value) {
        if (!value.matches(Constants.ALPHANUMERIC_REGEX)) {
            return field + ": " + value + " contains invalid characters. Only alphanumeric characters, hyphens, underscores, and periods allowed.";
        }
        return null;
    }

    /**
     * The checkspace method checks if the text contains any spaces
     * @param field the field being checked
     * @param value the actual value to be checked
     * @return the error message concat as str or null
     */
    public static String checkSpace(String field, String value) {
        if (value.contains(" ")) {
            return field + ": " + value + " contains spaces. Cannot contain spaces.";
        }
        return null;
    }
}
