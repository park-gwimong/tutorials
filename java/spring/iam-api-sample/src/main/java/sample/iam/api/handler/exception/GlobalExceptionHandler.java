package sample.iam.api.handler.exception;

import sample.iam.api.exception.DatabaseException;
import sample.iam.api.exception.DifferentSessionException;
import sample.iam.api.exception.HaveChildrenException;
import sample.iam.api.exception.NotFoundException;
import sample.iam.api.exception.NotSupportException;
import sample.iam.api.exception.SessionNotFoundException;
import sample.iam.api.exception.UnknownException;
import sample.iam.api.exception.ValidationException;
import sample.iam.api.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * The type Global exception handler.
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

  private final AppProperties appProperties;

  /**
   * Handle exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = Exception.class)
  public String handleException(Exception e) {
    return handleBaseException(e);
  }

  /**
   * Handle unknown exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = UnknownException.class)
  public String handleUnknownException(UnknownException e) {
    return handleBaseException(e);
  }

  /**
   * Handle null pointer exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = NullPointerException.class)
  public String handleNullPointerException(NullPointerException e) {
    return handleBaseException(e);
  }

  /**
   * Handle validation exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = ValidationException.class)
  public String handleValidationException(ValidationException e) {
    return handleBaseException(e);
  }

  /**
   * Handle database exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = DatabaseException.class)
  public String handleDatabaseException(DatabaseException e) {
    return handleBaseException(e);
  }

  /**
   * Handle not support exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = NotSupportException.class)
  public String handleNotSupportException(NotSupportException e) {
    return handleBaseException(e);
  }

  /**
   * Handle have children exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = HaveChildrenException.class)
  public String handleHaveChildrenException(HaveChildrenException e) {
    return handleBaseException(e);
  }

  /**
   * Handle not found exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = {NotFoundException.class, SessionNotFoundException.class})
  public String handleNotFoundException(NotFoundException e) {

    return handleBaseException(e);
  }

  /**
   * Handle different session exception string.
   *
   * @param e the e
   * @return the string
   */
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(value = DifferentSessionException.class)
  public String handleDifferentSessionException(DifferentSessionException e) {
    return handleBaseException(e);
  }

  private String handleBaseException(Exception e) {
    log.error("Exception : ", e);
    if (appProperties.getIsTestMode()) {
      log.debug(appProperties.getServerAddress());
      return e.getMessage();
    } else {
      return "An exception has occurred.";
    }
  }
}