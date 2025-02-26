package ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsernameOrCorporateEmail;

import java.util.regex.Pattern;

/**
 * Custom validator for validating a user identifier, which can be either a username or a corporate email.
 * This validator ensures that the provided identifier is either a valid username or a valid corporate email.
 * <p>
 * Validation rules:
 * - If the identifier is a username, it must contain Latin letters and may include numbers and special characters such as `.`, `_`, or `-`.
 * - If the identifier is a corporate email, it must match the format `name.surname@faculty.khpi.edu.ua`.
 * - The identifier must not be null or blank.
 * <p>
 * This validator is applied through the `@ValidUsernameOrCorporateEmail` annotation.
 * <p>
 * Dependencies:
 * - The validator uses regular expressions (regex) defined in `ValidationConstants.REGEX_USERNAME` and `ValidationConstants.REGEX_CORPORATE_EMAIL` to validate the username and corporate email formats.
 *
 * @author Inessa Repeshko CS-222a
 * @see ValidUsernameOrCorporateEmail
 * @see ValidationConstants
 * @see Pattern
 * @see ConstraintValidator
 */

public class UsernameOrCorporateEmailValidator implements ConstraintValidator<ValidUsernameOrCorporateEmail, String> {
    private static final Pattern USERNAME_PATTERN = Pattern.compile(ValidationConstants.REGEX_USERNAME);
    private static final Pattern CORPORATE_EMAIL_PATTERN = Pattern.compile(ValidationConstants.REGEX_CORPORATE_EMAIL);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The user identifier must not be blank or null.")
                    .addConstraintViolation();
            return false;
        }

        if (!USERNAME_PATTERN.matcher(value).matches() && !CORPORATE_EMAIL_PATTERN.matcher(value).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The user identifier must be a valid username " +
                            "(must contain Latin letters and may contain numbers and special characters '.', '_', '-') " +
                            "or corporate email (should match the format 'name.surname@faculty.khpi.edu.ua').")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

