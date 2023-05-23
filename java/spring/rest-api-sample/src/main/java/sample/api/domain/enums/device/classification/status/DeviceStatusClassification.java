package sample.api.domain.enums.device.classification.status;

import sample.api.domain.enums.LegacyCommonType;

/**
 * The enum Device status classification.
 */
public enum DeviceStatusClassification implements LegacyCommonType {

  /**
   * The New device.
   */
  NEW_DEVICE("NEW", "This is the first device use."),

  /**
   * The Reaper device.
   */
  REAPER_DEVICE("RFB", "This is a Ripper product.");


  private final String legacyCode;

  private final String description;


  DeviceStatusClassification(String legacyCode, String description) {
    this.legacyCode = legacyCode;
    this.description = description;
  }


  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public String getLegacyCode() {
    return this.legacyCode;
  }
}
