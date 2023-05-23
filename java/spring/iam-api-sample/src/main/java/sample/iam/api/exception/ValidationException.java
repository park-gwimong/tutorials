package sample.iam.api.exception;

import sample.iam.api.messages.ExceptionMessages;

public class ValidationException extends Exception {

  private static final long serialVersionUID = 1L;

  public ValidationException() {
    super(ExceptionMessages.DefaultException);
  }

  public ValidationException(String msg) {
    super(msg);
  }

  public ValidationException(String msg, Throwable exception) {
    super(msg, exception);
  }


}
