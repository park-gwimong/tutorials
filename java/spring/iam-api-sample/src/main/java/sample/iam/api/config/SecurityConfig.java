package sample.iam.api.config;

import sample.iam.api.filter.CustomAuthenticationFilter;
import sample.iam.api.filter.CustomJwtFilter;
import sample.iam.api.properties.AppProperties;
import sample.iam.api.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Security config.
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private final AppProperties appProperties;

  private final JwtProvider jwtProvider;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final AuthenticationFailureHandler authenticationFailureHandler;

  /**
   * The Get whitelist.
   */
  final String[] GET_WHITELIST = new String[]{
      "/auth/login",
  };

  final String[] POST_WHITELIST = new String[]{
      "/api/users",
  };


  /**
   * Configure web security customizer.
   *
   * @return the web security customizer
   */
  @Bean
  public WebSecurityCustomizer configure() {
    if (appProperties.getIsTestMode()) {
      return (web) -> web.ignoring().mvcMatchers(
          "/docs/**"
      );
    }
    return null;
  }

  /**
   * Security filter chain security filter chain.
   *
   * @param http the http
   * @return the security filter chain
   * @throws Exception the exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .httpBasic().disable()
        .formLogin().disable()
        .cors().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint) // 인증 실패
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, GET_WHITELIST).permitAll() // 해당 GET URL 모두 허용
        .antMatchers(HttpMethod.POST, POST_WHITELIST).permitAll() // 해당 POST URL 모두 허용
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(authenticationFilter(http), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  /**
   * Password encoder password encoder.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Authentication manager.
   *
   * @param authConfiguration the auth configuration
   * @return the authentication manager
   * @throws Exception the exception
   */
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration)
      throws Exception {
    return authConfiguration.getAuthenticationManager();
  }

  /**
   * Authentication filter custom authentication filter.
   *
   * @param http the http
   * @return the custom authentication filter
   * @throws Exception the exception
   */
  public CustomAuthenticationFilter authenticationFilter(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
        authenticationManager(http.getSharedObject((AuthenticationConfiguration.class))));

    // 필터 URL 설정
    customAuthenticationFilter.setFilterProcessesUrl("/auth/login");

    // 인증 성공 핸들러
    customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

    // 인증 실패 핸들러
    customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

    // Bean Factory 의한 모든 property 설정 뒤 실행
    customAuthenticationFilter.afterPropertiesSet();
    return customAuthenticationFilter;
  }

  /**
   * Jwt filter custom jwt filter.
   *
   * @return the custom jwt filter
   */
  public CustomJwtFilter jwtFilter() {
    return new CustomJwtFilter(jwtProvider);
  }
}