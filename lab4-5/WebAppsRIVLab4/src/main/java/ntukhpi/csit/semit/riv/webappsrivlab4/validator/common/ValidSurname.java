package ntukhpi.csit.semit.riv.webappsrivlab4.validator.common;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;

import java.lang.annotation.*;

/**
 * Custom validation annotation for validating surnames.
 * This annotation ensures that the annotated field or parameter:
 * - Is not blank.
 * - Has a length between 1 and 50 characters.
 * - Matches the specified pattern for valid surnames, allowing only Ukrainian letters,
 * with support for complex surnames containing spaces or dashes.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures the surname is not blank or empty.
 * - `@Size`: Enforces the length of the surname to be between 1 and 50 characters.
 * - `@Pattern`: Validates the surname against a regular expression defined in `ValidationConstants.REGEX_NAMES`.
 * <p>
 * Typical usage:
 * - Annotate fields or method parameters representing surnames to enforce these validation rules.
 * - Example: `@ValidSurname private String surname;`
 * <p>
 * Dependencies:
 * - Uses Jakarta Bean Validation constraints and custom-defined constants for regular expressions.
 * <p>
 * This annotation is commonly used in entity or DTO validation layers to ensure consistent formatting of surnames.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotBlank
 * @see Size
 * @see Pattern
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "The surname must not be blank.")
@Size(min = 1, max = 50, message = "The surname length should be between 1 to 50 characters.")
@Pattern(regexp = ValidationConstants.REGEX_NAMES,
        message = "The surname can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")

public @interface ValidSurname {
    String message() default "Invalid surname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

