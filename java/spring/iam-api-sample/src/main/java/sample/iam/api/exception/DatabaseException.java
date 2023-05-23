package sample.iam.api.exception;

import sample.iam.api.messages.ExceptionMessages;

public class DatabaseException extends Exception {

  private static final long serialVersionUID = 1L;

  public DatabaseException() {
    super(ExceptionMessages.DBException);
  }

  public DatabaseException(Throwable exception) {
    super(ExceptionMessages.DBException, exception);
  }

  public DatabaseException(String msg) {
    super(msg);
  }

  public DatabaseException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
