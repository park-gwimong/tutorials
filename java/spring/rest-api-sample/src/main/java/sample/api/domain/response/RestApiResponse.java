package sample.api.domain.response;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The type Rest api response.
 *
 * @param <T> the type parameter
 */
@NoArgsConstructor
public class RestApiResponse<T> {

  /**
   * Success response entity.
   *
   * @param <T>  the type parameter
   * @param data the data
   * @return the response entity
   */
  public static <T> ResponseEntity<?> success(T data) {
    return ResponseEntity.status(HttpStatus.OK).body(data);
  }

  /**
   * Success with no content response entity.
   *
   * @return the response entity
   */
  public static ResponseEntity<?> successWithNoContent() {
    return ResponseEntity.status(HttpStatus.OK).body(true);
  }

  /**
   * Fail response entity.
   *
   * @return the response entity
   */
  public static ResponseEntity<?> fail() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
  }

  /**
   * Exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  public static ResponseEntity<?> exception(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
  }

}