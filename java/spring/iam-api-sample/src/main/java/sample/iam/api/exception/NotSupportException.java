package sample.iam.api.exception;

import sample.iam.api.messages.ExceptionMessages;

public class NotSupportException extends Exception {

  private static final long serialVersionUID = 1L;

  public NotSupportException() {
    super(ExceptionMessages.DBException);
  }

  public NotSupportException(Throwable exception) {
    super(ExceptionMessages.DBException, exception);
  }

  public NotSupportException(String dataType) {
    super("Not support type : [" + dataType + "]");
  }

  public NotSupportException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
