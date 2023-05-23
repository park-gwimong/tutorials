package sample.api.exception;

import sample.api.messages.AccessMessages;

/**
 * The type Different session exception.
 */
public class DifferentSessionException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Different session exception.
   */
  public DifferentSessionException() {
    super(AccessMessages.SESSION_DIFFERENT);
  }

  /**
   * Instantiates a new Different session exception.
   *
   * @param msg the msg
   */
  public DifferentSessionException(String msg) {
    super(msg);
  }

  /**
   * Instantiates a new Different session exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public DifferentSessionException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
