package sample.api.domain.enums.device.classification.os;

import sample.api.domain.enums.LegacyCommonType;

/**
 * The enum Device os classification.
 */
public enum DeviceOsClassification implements LegacyCommonType {

  /**
   * The Windows os.
   */
  WINDOWS_OS("WIN", "Windows OS"),

  /**
   * The Android os.
   */
  ANDROID_OS("ADR", "Android OS"),
  /**
   * The Ios os.
   */
  IOS_OS("IOS", "IOS OS");


  private final String legacyCode;

  private final String description;


  DeviceOsClassification(String legacyCode, String description) {
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
