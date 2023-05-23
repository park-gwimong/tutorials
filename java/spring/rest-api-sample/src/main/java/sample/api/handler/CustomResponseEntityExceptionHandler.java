package sample.api.handler;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Custom response entity exception handler.
 */
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle constraint violation response entity.
   *
   * @param e       the e
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e,
      WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }
}