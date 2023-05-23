package sample.api.config;

import javax.servlet.ServletContext;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * The type Authentication filter configuration.
 */
@Configuration
public class AuthenticationFilterConfiguration implements ServletContextInitializer {


  /**
   * Instantiates a new Authentication filter configuration.
   */
  public AuthenticationFilterConfiguration() {
  }

  @Override
  public void onStartup(ServletContext context) {
    context.setInitParameter("defaultHtmlEscape", "true");
  }
}
