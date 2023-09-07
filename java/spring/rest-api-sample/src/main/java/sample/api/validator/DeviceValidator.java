package sample.api.validator;

import java.util.regex.Pattern;

import org.springframework.lang.NonNull;

import sample.api.domain.dto.RequestDeviceDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sample.api.messages.ValidateMessages;


/**
 * The type Device validator.
 */
@Component
public class DeviceValidator implements Validator {

    @Override
    public boolean supports(Class<?> c) {
        return RequestDeviceDTO.class.equals(c);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        RequestDeviceDTO requestDeviceDto = (RequestDeviceDTO) o;

        validateClassification(requestDeviceDto, errors);
        validateSn(requestDeviceDto, errors);
    }

    /**
     * Validate classification.
     *
     * @param requestDeviceDto the device entity
     * @param errors           the errors
     */
    public void validateClassification(RequestDeviceDTO requestDeviceDto, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statusClassification", ValidateMessages.NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeClassification", ValidateMessages.NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "osClassification", ValidateMessages.NOT_EMPTY);

    }

    /**
     * Validate sn.
     *
     * @param requestDeviceDto the device entity
     * @param errors           the errors
     */
    public void validateSn(RequestDeviceDTO requestDeviceDto, Errors errors) {

        String sn = requestDeviceDto.getSn();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sn", ValidateMessages.NOT_EMPTY);

        if (sn != null) {
            if (sn.length() < 2 || sn.length() > 32) {
                errors.rejectValue("sn", "Size.Device.sn");
            }

            if (!Pattern.matches("\\w+$", sn)) {
                errors.rejectValue("sn", "Contains.Device.sn");
            }
        }

    }
}
