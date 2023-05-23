package sample.api.handler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import sample.api.properties.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The type Custom bind exception handler.
 */
@RestControllerAdvice
public class CustomBindExceptionHandler {

  private final MessageSource messageSource;

  private final AppProperties appProperties;

  /**
   * Instantiates a new Custom bind exception handler.
   *
   * @param messageSource the message source
   * @param appProperties the app properties
   */
  @Autowired
  public CustomBindExceptionHandler(MessageSource messageSource, AppProperties appProperties) {
    this.messageSource = messageSource;
    this.appProperties = appProperties;
  }

  /**
   * Handle bind exception map.
   *
   * @param bindException the bind exception
   * @return the map
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleBindException(BindException bindException) {
    Map<String, String> errors = new HashMap<>();
    for (ObjectError error : bindException.getAllErrors()) {
      String message = messageSource.getMessage(Objects.requireNonNull(error.getCode()), null,
          new Locale(appProperties.getLocal()));
      errors.put(((FieldError) error).getField(), message);
    }

    return errors;
  }
}
