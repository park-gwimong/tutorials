package sample.iam.api.properties;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * The type App properties.
 */
@Component
@Data
public class AppProperties {

  private final Environment environment;

  private String serverAddress;

  @Value("${locale}")
  private String local;


  private boolean isTestMode;

  /**
   * Gets is test mode.
   *
   * @return the is test mode
   */
  public boolean getIsTestMode() {
    String active = environment.getProperty("spring.profiles.active");
    return active != null && (active.equals("local") || active.equals("test"));
  }

  /**
   * Gets server address.
   *
   * @return the server address
   */
  public String getServerAddress() {

    InetAddress ip;
    try {
      ip = InetAddress.getLocalHost();
      serverAddress = ip.getHostAddress();

    } catch (UnknownHostException e) {

      e.printStackTrace();
    }
    return serverAddress;
  }
}
