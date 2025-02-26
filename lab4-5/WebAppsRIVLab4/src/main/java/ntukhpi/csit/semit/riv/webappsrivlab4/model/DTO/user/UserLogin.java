package ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidPassword;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsernameOrCorporateEmail;

/**
 * Data Transfer Object (DTO) for user login functionality.
 * This class encapsulates login-related information for authentication purposes.
 * <p>
 * Key functionalities:
 * - Represents the data required for user login.
 * - Supports both corporate email and username as valid login identifiers.
 * - Ensures secure handling of user passwords with validation.
 * <p>
 * Key attributes:
 * - `corporateEmailOrUsername`: Identifier for login, can be either corporate email or username.
 * - `password`: User's password for authentication.
 * <p>
 * Validation:
 * - `@ValidUsernameOrCorporateEmail`: Ensures the input is a valid username or corporate email.
 * - `@ValidPassword`: Ensures the password meets defined security requirements.
 * <p>
 * Utility Methods:
 * - `getUserLoginFromUserTestData`: Constructs a `UserLogin` object from `UserTestData`.
 * - `getUserLoginFromUserEntity`: Constructs a `UserLogin` object from a `UserEntity`.
 * <p>
 * Relationships:
 * - Tightly integrated with `UserEntity` for mapping user login information.
 * - Supports processes like authentication and user login validation.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see UserTestData
 * @see ValidUsernameOrCorporateEmail
 * @see ValidPassword
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    @ValidUsernameOrCorporateEmail
    private String corporateEmailOrUsername;

    @ValidPassword
    private String password;

    public static UserLogin getUserLoginFromUserTestData(UserTestData userTestData) {
        return new UserLogin(
                userTestData.getCorporateEmail() != null ? userTestData.getCorporateEmail() : userTestData.getUsername(),
                userTestData.getPassword()
        );
    }

    public static UserLogin getUserLoginFromUserEntity(UserEntity userEntity) {
        return new UserLogin(
                userEntity.getCorporateEmail() != null ? userEntity.getCorporateEmail() : userEntity.getUsername(),
                userEntity.getPassword()
        );
    }
}
