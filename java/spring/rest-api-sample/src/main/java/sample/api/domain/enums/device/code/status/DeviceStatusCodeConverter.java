package sample.api.domain.enums.device.code.status;

import javax.persistence.Convert;
import sample.api.domain.enums.AbstractLegacyEnumAttributeConverter;

/**
 * The type Device status code converter.
 */
@Convert
public class DeviceStatusCodeConverter extends
    AbstractLegacyEnumAttributeConverter<DeviceStatusCode> {

  /**
   * The constant ENUM_NAME.
   */
  public static final String ENUM_NAME = "단말기 상태 코드";

  /**
   * Instantiates a new Device status code converter.
   */
  public DeviceStatusCodeConverter() {
    super(false, ENUM_NAME, DeviceStatusCode.class);
  }
}
