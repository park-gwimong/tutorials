package sample.api.domain.enums.device.code.status;

import sample.api.domain.enums.LegacyCommonType;

/**
 * The enum Device status code.
 */
public enum DeviceStatusCode implements LegacyCommonType {

  /**
   * The Possession.
   */
  POSSESSION("00", "It is in possession."),
  /**
   * The Rental.
   */
  RENTAL("01", "It is in rental condition.");


  private final String legacyCode;

  private final String description;


  DeviceStatusCode(String legacyCode, String description) {
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

