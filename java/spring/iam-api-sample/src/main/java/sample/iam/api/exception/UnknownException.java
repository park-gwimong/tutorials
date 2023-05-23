package sample.iam.api.exception;

import sample.iam.api.messages.ExceptionMessages;

public class UnknownException extends Exception {

  private static final long serialVersionUID = 1L;

  public UnknownException() {
    super(ExceptionMessages.DefaultException);
  }


  public UnknownException(Exception e) {
    super(e);
  }

  public UnknownException(String msg) {
    super(msg);
  }

  public UnknownException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
