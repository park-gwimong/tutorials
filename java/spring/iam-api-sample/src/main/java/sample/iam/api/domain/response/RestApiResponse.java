package sample.iam.api.domain.response;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
public class RestApiResponse<T> {

  public static <T> ResponseEntity<?> success(T data) {
    return ResponseEntity.status(HttpStatus.OK).body(data);
  }

  public static ResponseEntity<?> successWithNoContent() {
    return ResponseEntity.status(HttpStatus.OK).body(true);
  }

  public static ResponseEntity<?> fail() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
  }

  public static ResponseEntity<?> exception(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
  }

}