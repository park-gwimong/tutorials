package sample.api;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * The type Main application.
 */
@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class MainApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(MainApplication.class);
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    servletContext.setInitParameter("defaultHtmlEscape", "false");
    super.onStartup(servletContext);
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }
}