package sample.iam.api.filter;

import sample.iam.api.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomJwtFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {

    // Request Header에서 토큰 추출
    String jwt = resolveToken(request);
    // Token 유효성 검사
    if (StringUtils.hasText(jwt) && jwtProvider.isValidToken(jwt)) {
      // 토큰으로 인증 정보를 추출
      Authentication authentication = jwtProvider.getAuthentication(jwt);
      // SecurityContext에 저장
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      log.error("Authentication failed. [" + jwt + "]");
    }

    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken)) {
      return bearerToken;
    }
    return null;
  }

}