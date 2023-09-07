package sample.api.messages;

/**
 * The type Exception messages.
 */
public class ExceptionMessages {

    private ExceptionMessages() throws IllegalAccessException {
        throw new IllegalAccessException("Messages class");
    }

    /**
     * The constant DefaultException.
     */
    public static final String DEFAULT_EXCEPTION = "Exception occurred.";

    /**
     * The constant NotFoundException.
     */
    public static final String NOT_FOUND_EXCEPTION = "The requested data could not be found.";

    /**
     * The constant NullPointerException.
     */
    public static final String NULL_POINTER_EXCEPTION = "Exception Null";

    /**
     * The constant ServletException.
     */
    public static final String SERVLET_EXCEPTION = "Exception Servlet";

    /**
     * The constant IOException.
     */
    public static final String IO_EXCEPTION = "Exception occurred while input-ouput";

    /**
     * The constant DBException.
     */
    public static final String DB_EXCEPTION = "Exception occurred while databases";

    /**
     * The constant PermissionException.
     */
    public static final String PERMISSION_EXCEPTION = "Not have permission";

}
