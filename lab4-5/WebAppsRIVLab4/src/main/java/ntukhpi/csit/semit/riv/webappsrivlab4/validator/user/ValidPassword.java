package ntukhpi.csit.semit.riv.webappsrivlab4.validator.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;

import java.lang.annotation.*;

/**
 * Custom annotation for validating passwords.
 * This annotation ensures that the annotated field or parameter meets specific password strength and formatting requirements:
 * - The password must not be blank.
 * - The password length must be between 8 and 50 characters.
 * - The password must include:
 * - Latin letters.
 * - At least one uppercase letter.
 * - At least one special character.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures the password is not blank, preventing empty or null values.
 * - `@Size`: Enforces a password length between 8 and 50 characters.
 * - `@Pattern`: Validates that the password matches the specified format in `ValidationConstants.REGEX_PASSWORD`.
 * <p>
 * Purpose:
 * - To validate password fields for security and compliance with application-defined rules.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter to enforce this constraint.
 * - Example: `@ValidPassword private String password;`
 * <p>
 * Dependencies:
 * - Requires `ValidationConstants.REGEX_PASSWORD` to define the password format.
 * <p>
 * This annotation is essential for applications that require strong password policies to enhance security and user authentication robustness.
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

@NotBlank(message = "The password must not be blank.")
@Size(min = 8, max = 50, message = "The password length should be between 8 to 50 characters.")
@Pattern(regexp = ValidationConstants.REGEX_PASSWORD,
        message = "The password must contain Latin letters, at least one capital letter and one special character.")

public @interface ValidPassword {
    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

