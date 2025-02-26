package ntukhpi.csit.semit.riv.webappsrivlab4.validator.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.UsernameOrCorporateEmailValidator;

import java.lang.annotation.*;

/**
 * Custom annotation for validating a user identifier, which can either be a username or a corporate email.
 * This annotation ensures that the annotated field or parameter meets the application's requirements for both username
 * and corporate email formats.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures that the user identifier is not empty or null.
 * - `@Size`: Validates the length of the identifier, ensuring it falls between 8 to 123 characters.
 * - `@UsernameOrCorporateEmailValidator`: A custom validator that checks whether the identifier is a valid username or corporate email.
 * <p>
 * Purpose:
 * - To ensure that user identifiers are valid, either as usernames or corporate email addresses.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter in a user-related class or method to enforce these constraints.
 * - Example: `@ValidUsernameOrCorporateEmail private String userIdentifier;`
 * <p>
 * Dependencies:
 * - The annotation relies on a custom validator `UsernameOrCorporateEmailValidator` to ensure the identifier is either a valid username or corporate email.
 * <p>
 * This annotation is crucial for user authentication scenarios where either a username or corporate email can be used to identify a user.
 *
 * @author Inessa Repeshko CS-222a
 * @see UsernameOrCorporateEmailValidator
 * @see NotBlank
 * @see Size
 * @see Payload
 */

@Constraint(validatedBy = {UsernameOrCorporateEmailValidator.class})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "The user identifier must not be blank.")
@Size(min = 8, max = 123, message = "The user identifier length should be between 8 to 123 characters.")

public @interface ValidUsernameOrCorporateEmail {
    String message() default "The user identifier must be a valid username or corporate email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

