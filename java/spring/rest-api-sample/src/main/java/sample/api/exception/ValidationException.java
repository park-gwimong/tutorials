package sample.api.exception;

import sample.api.messages.ExceptionMessages;

/**
 * The type Validation exception.
 */
public class ValidationException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Validation exception.
   */
  public ValidationException() {
    super(ExceptionMessages.DefaultException);
  }

  /**
   * Instantiates a new Validation exception.
   *
   * @param msg the msg
   */
  public ValidationException(String msg) {
    super(msg);
  }

  /**
   * Instantiates a new Validation exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public ValidationException(String msg, Throwable exception) {
    super(msg, exception);
  }


}
