package sample.iam.api.exception;

import sample.iam.api.messages.AccessMessages;

public class SessionNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public SessionNotFoundException() {
    super(AccessMessages.SESSION_NOT_EXIST);
  }

  public SessionNotFoundException(String msg) {
    super(msg);
  }

  public SessionNotFoundException(String msg, Throwable exception) {
    super(msg, exception);
  }
}
