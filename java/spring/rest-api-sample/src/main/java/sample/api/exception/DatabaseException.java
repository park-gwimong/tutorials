package sample.api.exception;

import sample.api.messages.ExceptionMessages;

/**
 * The type Database exception.
 */
public class DatabaseException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Database exception.
   */
  public DatabaseException() {
    super(ExceptionMessages.DBException);
  }

  /**
   * Instantiates a new Database exception.
   *
   * @param exception the exception
   */
  public DatabaseException(Throwable exception) {
    super(ExceptionMessages.DBException, exception);
  }

  /**
   * Instantiates a new Database exception.
   *
   * @param msg the msg
   */
  public DatabaseException(String msg) {
    super(msg);
  }

  /**
   * Instantiates a new Database exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public DatabaseException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
