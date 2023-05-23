package sample.api.properties;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * The type App properties.
 */
@Component
@PropertySource("classpath:application.properties")
@Data
public class AppProperties {

  private String serverAddress;

  @Value("${locale}")
  private String local;

  @Value("${spring.profiles.active}")
  private String active;

  private boolean isTestMode;

  /**
   * Gets is test mode.
   *
   * @return the is test mode
   */
  public boolean getIsTestMode() {
    return active.equals("local");
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
