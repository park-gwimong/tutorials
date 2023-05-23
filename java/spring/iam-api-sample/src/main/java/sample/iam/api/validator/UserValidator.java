package sample.iam.api.validator;

import java.util.regex.Pattern;
import sample.iam.api.domain.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

  @Override
  public boolean supports(Class<?> c) {
    return UserEntity.class.equals(c);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserEntity userEntity = (UserEntity) o;

    validateNotEmpty(errors);
    validateEmail(userEntity, errors);
  }

  public void validateNotEmpty(Errors errors) {

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "NotEmpty");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

  }

  public void validateEmail(UserEntity userEntity, Errors errors) {

    String email = userEntity.getEmail();
    if (email != null) {
      if (!Pattern.matches("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", email)) {
        errors.rejectValue("email", "Contains.User.email");
      }
    }
  }
}
