package ntukhpi.csit.semit.riv.webappsrivlab4.validator.student;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

/**
 * Custom annotation for validating funding types.
 * This annotation ensures that the annotated field or parameter meets specific domain constraints:
 * - The funding type must not be null.
 * <p>
 * Validation rules:
 * - `@NotNull`: Ensures the value is not null, preventing the absence of a funding type.
 * <p>
 * Purpose:
 * - To validate funding type fields in entities, DTOs, or other domain models, ensuring that valid funding type data is always provided.
 * <p>
 * Usage:
 * - Annotate the relevant field or parameter to enforce this constraint.
 * - Example: `@ValidFundingType private FundingType fundingType;`
 * <p>
 * Dependencies:
 * - Uses Jakarta Bean Validation annotations for built-in constraint validation.
 * <p>
 * This annotation is ideal for ensuring that funding type fields are always specified and comply with domain-specific requirements.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotNull
 * @see Payload
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotNull(message = "Funding type must not be null.")

public @interface ValidFundingType {
    String message() default "Invalid funding type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

