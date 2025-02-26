package ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;

import java.lang.annotation.*;

/**
 * Custom validation annotation for validating case numbers.
 * This annotation ensures that the annotated field or parameter:
 * - Is not blank.
 * - Falls within the defined size range.
 * - Matches the specified format for a valid case number.
 * <p>
 * Validation rules:
 * - `@NotBlank`: Ensures the case number is not null or empty.
 * - `@Size`: Limits the length of the case number between 5 and 20 characters.
 * - `@Pattern`: Enforces a specific format defined by the regular expression `ValidationConstants.REGEX_CASE_NUMBER`.
 * <p>
 * Typical usage:
 * - Apply this annotation to fields or method parameters representing case numbers in entities or DTOs.
 * - Example: `@ValidCaseNumber private String caseNumber;`
 * <p>
 * Dependencies:
 * - Built-in constraints (`@NotBlank`, `@Size`, and `@Pattern`) for general validation.
 * - Custom regex defined in `ValidationConstants` for domain-specific format enforcement.
 * <p>
 * This annotation is commonly used in the validation layers of entities or DTOs to ensure data consistency
 * and adherence to the required format for case numbers.
 *
 * @author Inessa Repeshko CS-222a
 * @see NotBlank
 * @see Size
 * @see Pattern
 * @see ValidationConstants
 */

@Constraint(validatedBy = {})
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

@NotBlank(message = "The case number must not be blank.")
@Size(min = 5, max = 20, message = "The case number size should be between 5 to 20 characters.")
@Pattern(regexp = ValidationConstants.REGEX_CASE_NUMBER, message = "The case number should be in the format 'LLLLLLLLLLNN-NNNN'.")

public @interface ValidCaseNumber {
    String message() default "Invalid case number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

