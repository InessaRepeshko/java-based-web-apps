package ntukhpi.csit.semit.riv.webappsrivlab4.validator.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for validating usernames.
 * This annotation ensures that the annotated field or parameter meets the application's username requirements.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures that the username is not empty or null.
 * - `@Size`: Validates the username length to be between 8 to 32 characters.
 * - `@Pattern`: Enforces the username format to match the specified regular expression (allowing Latin letters, numbers, and certain special characters like '.', '_', and '-').
 * <p>
 * Purpose:
 * - To ensure that usernames are valid and comply with the application's formatting and length constraints.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter in a user-related class or method to enforce these constraints.
 * - Example: `@ValidUsername private String username;`
 * <p>
 * Dependencies:
 * - Relies on the `ValidationConstants.REGEX_USERNAME` to define the valid pattern for usernames.
 * - The pattern allows Latin letters, numbers, and specific special characters (`.`, `_`, `-`).
 * <p>
 * This annotation is essential for user registration, login, and other functionalities involving usernames, ensuring that usernames are valid and consistent across the application.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotBlank
 * @see Size
 * @see Pattern
 * @see Payload
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "The username must not be blank.")
@Size(min = 8, max = 32, message = "The username length should be between 8 to 32 characters.")
@Pattern(regexp = ValidationConstants.REGEX_USERNAME, message = "The username must contain Latin letters and may contain numbers and special characters '.', '_', '-'.")

public @interface ValidUsername {
    String message() default "Invalid username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

