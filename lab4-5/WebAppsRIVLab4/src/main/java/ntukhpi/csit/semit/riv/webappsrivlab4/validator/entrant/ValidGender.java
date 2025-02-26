package ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.*;

/**
 * Custom validation annotation for validating gender values.
 * This annotation ensures that the annotated field or parameter:
 * - Is not null.
 * <p>
 * Validation rules:
 * - `@NotNull`: Ensures the gender value is not null.
 * <p>
 * Typical usage:
 * - Apply this annotation to fields or method parameters representing gender in entities or DTOs.
 * - Example: `@ValidGender private Gender gender;`
 * <p>
 * Purpose:
 * - Ensures that gender values are not left undefined in the application, adhering to domain requirements.
 * <p>
 * Dependencies:
 * - Built-in constraint (`@NotNull`) for null-check validation.
 * <p>
 * This annotation is commonly used in the validation layers of entities or DTOs to enforce mandatory gender specification.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotNull
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotNull(message = "The gender must not be null.")

public @interface ValidGender {
    String message() default "Invalid gender";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

