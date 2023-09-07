package sample.api.config;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import sample.api.interceptror.ControllerInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.DefaultMessageCodesResolver.Format;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Web mvc config.
 */
@Configuration
@EnableWebMvc
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

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/docs/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
        .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
  }

  @Override
  public MessageCodesResolver getMessageCodesResolver() {
    DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    codesResolver.setMessageCodeFormatter(Format.POSTFIX_ERROR_CODE);
    return codesResolver;
  }

  /**
   * Message source message source.
   *
   * @return the message source
   */
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

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(mappingJackson2HttpMessageConverter());
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    return new MappingJackson2HttpMessageConverter(builder.build());
  }
}


