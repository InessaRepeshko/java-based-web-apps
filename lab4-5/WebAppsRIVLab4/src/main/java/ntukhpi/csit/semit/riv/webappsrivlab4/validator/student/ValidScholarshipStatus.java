package ntukhpi.csit.semit.riv.webappsrivlab4.validator.student;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

/**
 * Custom annotation for validating scholarship statuses.
 * This annotation ensures that the annotated field or parameter meets specific domain constraints:
 * - The scholarship status must not be null.
 * <p>
 * Validation rules:
 * - `@NotNull`: Ensures the value is not null, preventing the absence of a scholarship status.
 * <p>
 * Purpose:
 * - To validate scholarship status fields in entities, DTOs, or other domain models, ensuring that valid scholarship status data is always provided.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter to enforce this constraint.
 * - Example: `@ValidScholarshipStatus private ScholarshipStatus scholarshipStatus;`
 * <p>
 * Dependencies:
 * - Uses Jakarta Bean Validation annotations for built-in constraint validation.
 * <p>
 * This annotation is ideal for ensuring that scholarship status fields are always specified and comply with domain-specific requirements.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotNull
 * @see Payload
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotNull(message = "Scholarship status must not be null.")

public @interface ValidScholarshipStatus {
    String message() default "Invalid scholarship status";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

