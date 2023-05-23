package sample.iam.api.exception;

import sample.iam.api.messages.AccessMessages;

public class DifferentSessionException extends Exception {

  private static final long serialVersionUID = 1L;

  public DifferentSessionException() {
    super(AccessMessages.SESSION_DIFFERENT);
  }

  public DifferentSessionException(String msg) {
    super(msg);
  }

  public DifferentSessionException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
