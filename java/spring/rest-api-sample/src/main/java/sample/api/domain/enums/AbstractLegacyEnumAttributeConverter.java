package sample.api.domain.enums;

import io.netty.util.internal.StringUtil;
import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import sample.api.exception.NotFoundException;
import sample.api.utils.LegacyEnumValueConvertUtils;

/**
 * The type Abstract legacy enum attribute converter.
 *
 * @param <E> the type parameter
 */
@Convert
public class AbstractLegacyEnumAttributeConverter<E extends Enum<E> & LegacyCommonType> implements
    AttributeConverter<E, String> {

  private final Class<E> enumClass;

  private final boolean nullable;

  private final String enumName;

  /**
   * Instantiates a new Abstract legacy enum attribute converter.
   *
   * @param nullable  the nullable
   * @param enumName  the enum name
   * @param enumClass the enum class
   */
  public AbstractLegacyEnumAttributeConverter(boolean nullable, String enumName,
      Class<E> enumClass) {
    this.nullable = nullable;
    this.enumName = enumName;
    this.enumClass = enumClass;
  }


  @Override
  public String convertToDatabaseColumn(E attribute) {
    if (!nullable && attribute == null) {
      try {
        throw new IllegalAccessException(String.format("%s(은)는 NULL로 저장 할 수 없습니다.", enumName));
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    return LegacyEnumValueConvertUtils.toLegacyCode(attribute);
  }

  @Override
  public E convertToEntityAttribute(String data) {
    if (!nullable && StringUtil.isNullOrEmpty(data)) {
      try {
        throw new IllegalAccessException(
            String.format("%s(이)가 NULL 혹은 Empty로(%s) 저장되어 있습니다.", enumName, data));
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    try {
      return LegacyEnumValueConvertUtils.ofLegacyCode(enumClass, data);
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
