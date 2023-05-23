package sample.api.domain.enums.device.classification.status;

import javax.persistence.Convert;
import sample.api.domain.enums.AbstractLegacyEnumAttributeConverter;

/**
 * The type Device status classification converter.
 */
@Convert
public class DeviceStatusClassificationConverter extends
    AbstractLegacyEnumAttributeConverter<DeviceStatusClassification> {

  /**
   * The constant ENUM_NAME.
   */
  public static final String ENUM_NAME = "단말기 상태 분류";

  /**
   * Instantiates a new Device status classification converter.
   */
  public DeviceStatusClassificationConverter() {
    super(false, ENUM_NAME, DeviceStatusClassification.class);
  }
}
