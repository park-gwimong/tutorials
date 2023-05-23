package sample.iam.api.exception;

import sample.iam.api.messages.ExceptionMessages;

public class NotFoundException extends Exception {

  private static final long serialVersionUID = -5345825923487658213L;

  public NotFoundException() {
    super(ExceptionMessages.NotFoundException);
  }

  public NotFoundException(String msg) {
    super(msg);
  }

  public NotFoundException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
