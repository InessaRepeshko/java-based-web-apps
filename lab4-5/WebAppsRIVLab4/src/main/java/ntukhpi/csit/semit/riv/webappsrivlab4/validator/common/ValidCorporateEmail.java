package ntukhpi.csit.semit.riv.webappsrivlab4.validator.common;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation for validating corporate email addresses.
 * This annotation ensures that the annotated field or parameter:
 * - Is not blank.
 * - Has a length between 17 and 123 characters.
 * - Matches the specific format for corporate email addresses.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures the email is not null or empty.
 * - `@Size`: Limits the length of the email to the range 17–123 characters.
 * - `@Email`: Validates the format of the email against a custom regex defined in `ValidationConstants.REGEX_CORPORATE_EMAIL`.
 * <p>
 * Typical usage:
 * - Annotate fields or method parameters in DTOs or entities to enforce corporate email validation rules.
 * - Example: `@ValidCorporateEmail private String corporateEmail;`
 * <p>
 * Dependencies:
 * - Utilizes standard Jakarta Bean Validation constraints like `@NotBlank`, `@Size`, and `@Email`.
 * - Relies on the `ValidationConstants.REGEX_CORPORATE_EMAIL` for regex pattern matching.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotBlank
 * @see Size
 * @see Email
 * @see ValidationConstants
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "The email must not be blank.")
@Size(min = 17, max = 123, message = "The corporate email length should be between 17 to 123 characters.")
@Email(regexp = ValidationConstants.REGEX_CORPORATE_EMAIL,
        message = "The corporate email should match the format 'name.surname@faculty.khpi.edu.ua'.")

public @interface ValidCorporateEmail {
    String message() default "Invalid corporate email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

