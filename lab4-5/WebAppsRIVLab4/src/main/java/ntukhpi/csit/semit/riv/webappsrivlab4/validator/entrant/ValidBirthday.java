package ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Past;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.BirthdayValidator;

import java.lang.annotation.*;

/**
 * Custom validation annotation for validating birthdays.
 * This annotation ensures that the annotated field or parameter:
 * - Represents a date in the past.
 * - Passes additional validation logic implemented in the `BirthdayValidator` class.
 * <p>
 * Validation rules:
 * - `@Past`: Ensures the date is strictly in the past.
 * - `BirthdayValidator`: Provides custom logic to validate domain-specific birthday constraints.
 * <p>
 * Typical usage:
 * - Annotate fields or method parameters representing birthdays to enforce these validation rules.
 * - Example: `@ValidBirthday private LocalDate birthday;`
 * <p>
 * Dependencies:
 * - Relies on Jakarta Bean Validation for basic date validation.
 * - Custom validation logic is implemented in the `BirthdayValidator` class.
 * <p>
 * This annotation is commonly used in entity or DTO validation layers to ensure consistent formatting of birthdays
 * and adherence to domain-specific rules.
 *
 * @author Inessa Repeshko CS-222a
 * @see Past
 * @see BirthdayValidator
 */

@Constraint(validatedBy = {BirthdayValidator.class})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@Past(message = "The birthday must be in the past.")

public @interface ValidBirthday {
    String message() default "Invalid birthday";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

