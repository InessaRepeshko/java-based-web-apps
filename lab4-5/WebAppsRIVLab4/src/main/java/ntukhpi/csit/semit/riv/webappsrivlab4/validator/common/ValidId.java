package ntukhpi.csit.semit.riv.webappsrivlab4.validator.common;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;

import java.lang.annotation.*;

/**
 * Custom validation annotation for validating entity IDs.
 * This annotation ensures that the annotated field or parameter:
 * - Is not null.
 * - Is a positive number.
 * <p>
 * Validation rules:
 * - `@NotNull`: Ensures the ID is not null.
 * - `@Positive`: Validates that the ID is a positive number (greater than zero).
 * <p>
 * Typical usage:
 * - Annotate fields or method parameters representing entity IDs to enforce these validation rules.
 * - Example: `@ValidId private Long id;`
 * <p>
 * Dependencies:
 * - Utilizes standard Jakarta Bean Validation constraints such as `@NotNull` and `@Positive`.
 * <p>
 * This annotation is commonly used in service and repository layers to validate IDs before processing.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotNull
 * @see Positive
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotNull(message = "The id must not be null.")
@Positive(message = "The id must be a positive number.")

public @interface ValidId {
    String message() default "Invalid id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

