package sample.api.utils;

import io.netty.util.internal.StringUtil;
import java.util.EnumSet;
import sample.api.exception.NotFoundException;
import sample.api.domain.enums.LegacyCommonType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Legacy enum value convert utils.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LegacyEnumValueConvertUtils {

  /**
   * Of legacy code t.
   *
   * @param <T>        the type parameter
   * @param enumClass  the enum class
   * @param legacyCode the legacy code
   * @return the t
   * @throws NotFoundException the not found exception
   */
  public static <T extends Enum<T> & LegacyCommonType> T ofLegacyCode(Class<T> enumClass,
      String legacyCode) throws NotFoundException {

    if (StringUtil.isNullOrEmpty(legacyCode)) {
      return null;
    }

    return EnumSet.allOf(enumClass)//
        .stream()//
        .filter(v -> v.getLegacyCode().equals(legacyCode)) //
        .findAny()//
        .orElseThrow(() -> new NotFoundException(legacyCode));
  }

  /**
   * To legacy code string.
   *
   * @param <T>       the type parameter
   * @param enumValue the enum value
   * @return the string
   */
  public static <T extends Enum<T> & LegacyCommonType> String toLegacyCode(T enumValue) {
    if (enumValue == null) {
      return "";
    }

    return enumValue.getLegacyCode();
  }
}
