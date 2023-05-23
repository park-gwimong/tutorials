package sample.iam.api.config;

import java.util.concurrent.TimeUnit;
import sample.iam.api.interceptror.ControllerInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Web mvc config.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
      "classpath:/static/",
      "classpath:/public/",
      "classpath:/docs/",
      "classpath:/",
      "classpath:/resources/",
      "classpath:/META-INF/resources/",
      "classpath:/META-INF/resources/webjars/"
  };

  /**
   * Add resource handlers.
   *
   * @param registry the registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 정적 자원의 경로를 허용
    registry.addResourceHandler("/docs/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
        .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource;
    messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:messages/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new ControllerInterceptor())//
        .addPathPatterns("/**")
        .excludePathPatterns("/docs/**");
  }

}