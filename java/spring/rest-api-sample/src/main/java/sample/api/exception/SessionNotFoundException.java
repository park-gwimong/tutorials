package sample.api.exception;

import sample.api.messages.AccessMessages;

/**
 * The type Session not found exception.
 */
public class SessionNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Session not found exception.
   */
  public SessionNotFoundException() {
    super(AccessMessages.SESSION_NOT_EXIST);
  }

  /**
   * Instantiates a new Session not found exception.
   *
   * @param msg the msg
   */
  public SessionNotFoundException(String msg) {
    super(msg);
  }

  /**
   * Instantiates a new Session not found exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public SessionNotFoundException(String msg, Throwable exception) {
    super(msg, exception);
  }
}
