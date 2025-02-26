package ntukhpi.csit.semit.riv.webappsrivlab4.validator.common;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;

import java.lang.annotation.*;

/**
 * Custom validation annotation for validating patronymic names.
 * This annotation ensures that the annotated field or parameter:
 * - Is not blank.
 * - Has a length between 1 and 50 characters.
 * - Matches the specified pattern for valid patronymics, allowing only Ukrainian letters,
 * with support for complex patronymics containing spaces or dashes.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures the patronymic is not blank or empty.
 * - `@Size`: Enforces the length of the patronymic to be between 1 and 50 characters.
 * - `@Pattern`: Validates the patronymic against a regular expression defined in `ValidationConstants.REGEX_NAMES`.
 * <p>
 * Typical usage:
 * - Annotate fields or method parameters representing patronymics to enforce these validation rules.
 * - Example: `@ValidPatronymic private String patronymic;`
 * <p>
 * Dependencies:
 * - Uses Jakarta Bean Validation constraints and custom-defined constants for regular expressions.
 * <p>
 * This annotation is commonly used in entity or DTO validation layers to ensure consistent formatting of patronymics.
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

@NotBlank(message = "The patronymic must not be blank.")
@Size(min = 1, max = 50, message = "The patronymic length should be between 1 to 50 characters.")
@Pattern(regexp = ValidationConstants.REGEX_NAMES,
        message = "The patronymic can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")

public @interface ValidPatronymic {
    String message() default "Invalid patronymic";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

