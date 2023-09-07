package sample.api.messages;

/**
 * The type Access messages.
 */
public class AccessMessages {

    private AccessMessages() throws IllegalAccessException {
        throw new IllegalAccessException("Messages class");
    }

    /**
     * The constant SESSION_NOT_EXIST.
     */
    public static final String SESSION_NOT_EXIST = "Current session information does not exist.\n" //
            + "Please login again.";

    /**
     * The constant SESSION_DIFFERENT.
     */
    public static final String SESSION_DIFFERENT = "The requested information differs from " //
            + "the session information.";
}
