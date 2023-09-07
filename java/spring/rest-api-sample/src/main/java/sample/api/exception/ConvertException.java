package sample.api.exception;

import sample.api.messages.ExceptionMessages;

/**
 * The type Different session exception.
 */
public class ConvertException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Different session exception.
     */
    public ConvertException() {
        super(ExceptionMessages.DEFAULT_EXCEPTION);
    }

    /**
     * Instantiates a new Different session exception.
     *
     * @param msg the msg
     */
    public ConvertException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Different session exception.
     *
     * @param msg       the msg
     * @param exception the exception
     */
    public ConvertException(String msg, Throwable exception) {
        super(msg, exception);
    }

}
