package sample.iam.api.handler.auth;

import sample.iam.api.domain.response.ApiResponse;
import sample.iam.api.domain.response.ApiResponseType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    ApiResponse.error(response, ApiResponseType.UNAUTHORIZED_RESPONSE);
  }

}
