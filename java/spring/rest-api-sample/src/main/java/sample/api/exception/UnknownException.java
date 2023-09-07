package sample.api.exception;

import sample.api.messages.ExceptionMessages;

/**
 * The type Unknown exception.
 */
public class UnknownException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Unknown exception.
   */
  public UnknownException() {
    super(ExceptionMessages.DEFAULT_EXCEPTION);
  }


  /**
   * Instantiates a new Unknown exception.
   *
   * @param e the e
   */
  public UnknownException(Exception e) {
    super(e);
  }

  /**
   * Instantiates a new Unknown exception.
   *
   * @param msg the msg
   */
  public UnknownException(String msg) {
    super(msg);
  }

  /**
   * Instantiates a new Unknown exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public UnknownException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
