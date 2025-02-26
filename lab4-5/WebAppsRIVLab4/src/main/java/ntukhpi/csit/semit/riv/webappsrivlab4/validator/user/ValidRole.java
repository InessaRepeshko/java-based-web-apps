package ntukhpi.csit.semit.riv.webappsrivlab4.validator.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

/**
 * Custom annotation for validating user roles.
 * This annotation ensures that the annotated field or parameter is not null and complies with the application's role requirements.
 * <p>
 * Validation rules:
 * - `@NotNull`: Ensures the role value is not null.
 * <p>
 * Purpose:
 * - To validate that a role is assigned and properly configured for user entities or parameters requiring a role.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter to enforce this constraint.
 * - Example: `@ValidRole private Role userRole;`
 * <p>
 * Dependencies:
 * - Relies on the `Role` enumeration or similar logic to define valid roles in the application.
 * <p>
 * This annotation is critical for maintaining data integrity and ensuring that user entities or related logic include a valid role, preventing null values from causing errors in the application flow.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotNull
 * @see Payload
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotNull(message = "Role must not be null.")

public @interface ValidRole {
    String message() default "Invalid role";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

