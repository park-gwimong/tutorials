package sample.api.domain.enums.device.classification.os;

import javax.persistence.Convert;
import sample.api.domain.enums.AbstractLegacyEnumAttributeConverter;

/**
 * The type Device os classification converter.
 */
@Convert
public class DeviceOsClassificationConverter extends
    AbstractLegacyEnumAttributeConverter<DeviceOsClassification> {

  /**
   * The constant ENUM_NAME.
   */
  public static final String ENUM_NAME = "OS 분류";

  /**
   * Instantiates a new Device os classification converter.
   */
  public DeviceOsClassificationConverter() {
    super(false, ENUM_NAME, DeviceOsClassification.class);
  }
}
