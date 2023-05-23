package sample.api.exception;

import sample.api.messages.ExceptionMessages;

/**
 * The type Have children exception.
 */
public class HaveChildrenException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Have children exception.
   */
  public HaveChildrenException() {
    super(ExceptionMessages.DBException);
  }

  /**
   * Instantiates a new Have children exception.
   *
   * @param exception the exception
   */
  public HaveChildrenException(Throwable exception) {
    super(ExceptionMessages.DBException, exception);
  }

  /**
   * Instantiates a new Have children exception.
   *
   * @param children the children
   */
  public HaveChildrenException(String children) {
    super("Can not delete. because it have children : [" + children + "]");
  }

  /**
   * Instantiates a new Have children exception.
   *
   * @param msg       the msg
   * @param exception the exception
   */
  public HaveChildrenException(String msg, Throwable exception) {
    super(msg, exception);
  }

}
