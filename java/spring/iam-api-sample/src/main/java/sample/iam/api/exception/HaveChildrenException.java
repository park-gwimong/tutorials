package sample.iam.api.exception;

import sample.iam.api.messages.ExceptionMessages;

public class HaveChildrenException extends Exception {

  private static final long serialVersionUID = 1L;

  public HaveChildrenException() {
    super(ExceptionMessages.DBException);
  }

  public HaveChildrenException(Throwable exception) {
    super(ExceptionMessages.DBException, exception);
  }

  public HaveChildrenException(String children) {
    super("Can not delete. because it have children : [" + children + "]");
  }

  public HaveChildrenException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
