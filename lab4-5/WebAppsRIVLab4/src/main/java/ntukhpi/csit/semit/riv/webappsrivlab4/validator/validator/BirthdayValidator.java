package ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidBirthday;

import java.time.LocalDate;

/**
 * Custom validator for validating a birthday.
 * This validator ensures that the annotated `LocalDate` field represents a valid birthday,
 * where the date must be within the range of 16 to 116 years ago from the current date.
 * <p>
 * Validation rules:
 * - The date must be no more than 116 years in the past (i.e., the person must not be older than 116 years).
 * - The date must be no less than 16 years ago (i.e., the person must be at least 16 years old).
 * <p>
 * The validator uses the current date (`LocalDate.now()`) to compute the valid range for the birthday.
 * If the provided birthday is outside this range, a custom error message is added to the constraint violation context.
 * <p>
 * This validator is applied through the `@ValidBirthday` annotation.
 *
 * @author Inessa Repeshko CS-222a
 * @see ValidBirthday
 * @see LocalDate
 * @see ConstraintValidator
 */

public class BirthdayValidator implements ConstraintValidator<ValidBirthday, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value != null) {
            LocalDate today = LocalDate.now();
            LocalDate minDate = today.minusYears(116);
            LocalDate maxDate = today.minusYears(16);

            if (value.isBefore(minDate) || value.isAfter(maxDate)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("The birthday must be between 16 and 110 years ago today.")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
