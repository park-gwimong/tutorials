package sample.api.exception;

import sample.api.messages.ExceptionMessages;

/**
 * The type Not found exception.
 */
public class NotFoundException extends Exception {

  private static final long serialVersionUID = -5345825923487658213L;

  /**
   * Instantiates a new Not found exception.
   */
  public NotFoundException() {
    super(ExceptionMessages.NotFoundException);
  }

  /**
   * Instantiates a new Not found exception.
   *
   * @param msg the msg
   */
  public NotFoundException(String msg) {
    super(msg);
  }

  /**
   * Instantiates a new Not found exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public NotFoundException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
