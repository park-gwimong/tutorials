package sample.api.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestApiResponse {

    private RestApiResponse() {
    }

    public static <D> ResponseEntity<D> success(D data) {
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    public static <D> ResponseEntity<D> successWithNoContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    public static <D> ResponseEntity<D> fail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public static ResponseEntity<Exception> exception(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }

}