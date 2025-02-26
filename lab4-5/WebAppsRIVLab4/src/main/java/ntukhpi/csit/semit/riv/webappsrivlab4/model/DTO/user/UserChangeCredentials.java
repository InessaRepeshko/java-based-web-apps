package ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidPassword;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;

/**
 * Data Transfer Object (DTO) for managing user credential changes.
 * This class is used to encapsulate data required for updating user credentials such as username and password.
 * <p>
 * Key functionalities:
 * - Represents a user’s new username and password during credential updates.
 * - Provides validation for the username and password fields to ensure security and integrity.
 * - Includes a utility method to create a `UserChangeCredentials` instance from a `UserEntity` object.
 * <p>
 * Key attributes:
 * - `username`: New username for the user.
 * - `password`: New password for the user.
 * - `confirmPassword`: Confirmation of the new password.
 * <p>
 * Validation:
 * - Username must comply with the validation rules defined by `@ValidUsername`.
 * - Password fields (`password`, `confirmPassword`) must comply with `@ValidPassword`.
 * <p>
 * Relationships:
 * - Utilizes the `UserEntity` class for generating instances based on existing user data.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see ValidUsername
 * @see ValidPassword
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangeCredentials {
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

    public static UserChangeCredentials getUserChangeCredentialsFromUserEntity(UserEntity userEntity) {
        return new UserChangeCredentials(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getPassword()
        );
    }
}
