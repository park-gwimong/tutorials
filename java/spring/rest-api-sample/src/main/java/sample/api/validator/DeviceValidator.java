package sample.api.validator;

import java.util.regex.Pattern;
import sample.api.domain.entity.DeviceEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * The type Device validator.
 */
@Component
public class DeviceValidator implements Validator {

  @Override
  public boolean supports(Class<?> c) {
    return DeviceEntity.class.equals(c);
  }

  @Override
  public void validate(Object o, Errors errors) {
    DeviceEntity deviceEntity = (DeviceEntity) o;

    validateClassification(deviceEntity, errors);
    validateSn(deviceEntity, errors);
  }

  /**
   * Validate classification.
   *
   * @param deviceEntity the device entity
   * @param errors       the errors
   */
  public void validateClassification(DeviceEntity deviceEntity, Errors errors) {

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statusClassification", "NotEmpty");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeClassification", "NotEmpty");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "osClassification", "NotEmpty");

  }

  /**
   * Validate sn.
   *
   * @param deviceEntity the device entity
   * @param errors       the errors
   */
  public void validateSn(DeviceEntity deviceEntity, Errors errors) {

    String sn = deviceEntity.getSn();
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sn", "NotEmpty");

    if (sn != null) {
      if (sn.length() < 2 || sn.length() > 32) {
        errors.rejectValue("sn", "Size.Device.sn");
      }

      //TODO : Change pattern
      if (!Pattern.matches("^[a-zA-Z0-9_]+$", sn)) {
        errors.rejectValue("sn", "Contains.Device.sn");
      }
    }

  }
}
