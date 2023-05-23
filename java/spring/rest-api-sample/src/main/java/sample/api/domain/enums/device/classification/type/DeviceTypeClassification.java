package sample.api.domain.enums.device.classification.type;

import sample.api.domain.enums.LegacyCommonType;

/**
 * The enum Device type classification.
 */
public enum DeviceTypeClassification implements LegacyCommonType {

  /**
   * The Tablet.
   */
  TABLET("TBL", "This is tablet."),
  /**
   * The Notebook.
   */
  NOTEBOOK("NTB", "This is notebook.");


  private final String legacyCode;

  private final String description;


  DeviceTypeClassification(String legacyCode, String description) {
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

