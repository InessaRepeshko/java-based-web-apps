package ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidPassword;

/**
 * Data Transfer Object (DTO) for managing user password changes.
 * This class is used to encapsulate data required for updating a user's password.
 * <p>
 * Key functionalities:
 * - Represents the new password and its confirmation during a password update process.
 * - Ensures that both the password and confirmation meet validation criteria.
 * <p>
 * Key attributes:
 * - `password`: The new password for the user.
 * - `confirmPassword`: Confirmation of the new password, which should match the `password`.
 * <p>
 * Validation:
 * - Both fields are validated using the `@ValidPassword` annotation to ensure strong password policies are enforced.
 * <p>
 * Relationships:
 * - Designed to be used in processes that involve password updates, often integrated with user management systems.
 *
 * @author Inessa Repeshko CS-222a
 * @see ValidPassword
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePassword {
    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;
}
