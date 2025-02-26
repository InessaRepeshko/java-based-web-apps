package ntukhpi.csit.semit.riv.webappsrivlab4.validator.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Custom annotation for validating encoded passwords.
 * This annotation ensures that the annotated field or parameter adheres to the specific format and requirements of an encoded password:
 * - The password must not be blank.
 * - The password must have a fixed length of 60 characters.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures the password is not blank, preventing empty or null values.
 * - `@Size`: Enforces that the password length is exactly 60 characters, which is standard for securely hashed passwords (e.g., BCrypt).
 * <p>
 * Purpose:
 * - To validate password fields that are expected to be encoded (e.g., hashed) in entities, DTOs, or other domain models.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter to enforce this constraint.
 * - Example: `@ValidEncodedPassword private String password;`
 * <p>
 * Dependencies:
 * - Uses Jakarta Bean Validation annotations for built-in constraint validation.
 * <p>
 * This annotation is tailored for applications where password storage must meet security standards, ensuring that passwords are securely encoded and comply with domain-specific requirements.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotBlank
 * @see Size
 * @see Payload
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "The password must not be blank.")
@Size(min = 60, max = 60, message = "The password should be 60 characters long.")

public @interface ValidEncodedPassword {
    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

