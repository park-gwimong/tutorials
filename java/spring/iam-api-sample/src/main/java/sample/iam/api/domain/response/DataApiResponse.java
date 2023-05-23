package sample.iam.api.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataApiResponse<T> extends ApiResponse {
    private T result;
}
