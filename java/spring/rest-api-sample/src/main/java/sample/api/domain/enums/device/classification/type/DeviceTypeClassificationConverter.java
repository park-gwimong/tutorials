package sample.api.domain.enums.device.classification.type;

import javax.persistence.Convert;
import sample.api.domain.enums.AbstractLegacyEnumAttributeConverter;

/**
 * The type Device type classification converter.
 */
@Convert
public class DeviceTypeClassificationConverter extends
    AbstractLegacyEnumAttributeConverter<DeviceTypeClassification> {

  /**
   * The constant ENUM_NAME.
   */
  public static final String ENUM_NAME = "단말기 타입 분류";

  /**
   * Instantiates a new Device type classification converter.
   */
  public DeviceTypeClassificationConverter() {
    super(false, ENUM_NAME, DeviceTypeClassification.class);
  }
}
