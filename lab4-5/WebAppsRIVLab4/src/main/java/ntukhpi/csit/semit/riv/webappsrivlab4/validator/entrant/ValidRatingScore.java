package ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.lang.annotation.*;

/**
 * Custom annotation for validating rating scores.
 * This annotation ensures that the annotated field or parameter meets specific domain constraints:
 * - The rating score must be a positive number.
 * - The value must be greater than 120.000 (exclusive).
 * - The value must be less than or equal to 200.000 (inclusive).
 * <p>
 * Validation rules:
 * - `@DecimalMin`: Enforces the minimum value constraint.
 * - `@DecimalMax`: Enforces the maximum value constraint.
 * - `@Positive`: Ensures the value is a positive number.
 * <p>
 * Purpose:
 * - To validate rating scores used in entities, DTOs, or other domain models, ensuring data consistency.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter to enforce these constraints.
 * - Example: `@ValidRatingScore private Double ratingScore;`
 * <p>
 * Dependencies:
 * - Uses Jakarta Bean Validation annotations for built-in constraint validation.
 * <p>
 * This annotation is ideal for ensuring that rating scores are within an acceptable range
 * and are positive, thus supporting domain-specific logic and data integrity.
 *
 * @author Inessa Repeshko CS-222a
 * @see DecimalMin
 * @see DecimalMax
 * @see Positive
 * @see Payload
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@DecimalMin(value = "120.000", inclusive = false, message = "The rating score must be greater than 120,000.")
@DecimalMax(value = "200.000", inclusive = true, message = "The rating score must be less than or equal to 200,000.")
@Positive(message = "The rating score must be a positive number.")

public @interface ValidRatingScore {
    String message() default "Invalid rating score";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

