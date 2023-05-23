package sample.api.exception;

import sample.api.messages.ExceptionMessages;

/**
 * The type Not support exception.
 */
public class NotSupportException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Not support exception.
   */
  public NotSupportException() {
    super(ExceptionMessages.DBException);
  }

  /**
   * Instantiates a new Not support exception.
   *
   * @param exception the exception
   */
  public NotSupportException(Throwable exception) {
    super(ExceptionMessages.DBException, exception);
  }

  /**
   * Instantiates a new Not support exception.
   *
   * @param dataType the data type
   */
  public NotSupportException(String dataType) {
    super("Not support type : [" + dataType + "]");
  }

  /**
   * Instantiates a new Not support exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public NotSupportException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
