package ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;

/**
 * Data Transfer Object (DTO) for handling user password reset requests.
 * This class encapsulates the necessary information for initiating a password reset process.
 * <p>
 * Key functionalities:
 * - Represents the data required to request a password reset.
 * - Ensures the corporate email format is valid before processing the reset request.
 * <p>
 * Key attributes:
 * - `corporateEmail`: The user's corporate email used for identification during the password reset process.
 * <p>
 * Validation:
 * - `@ValidCorporateEmail`: Ensures the email follows a valid corporate format.
 * <p>
 * Utility Methods:
 * - `getUserResetPasswordFromUserTestData`: Creates a `UserResetPassword` object from `UserTestData` for testing purposes.
 * - `getUserResetPasswordFromUserEntity`: Creates a `UserResetPassword` object from `UserEntity` for handling actual user entities.
 * <p>
 * Relationships:
 * - Integrates with the `UserEntity` for fetching the corporate email for password reset.
 * - Utilized alongside `UserTestData` for mock testing or data population scenarios.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserTestData
 * @see UserEntity
 * @see ValidCorporateEmail
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResetPassword {
    @ValidCorporateEmail
    private String corporateEmail;

    public static UserResetPassword getUserResetPasswordFromUserTestData(@Valid UserTestData userTestData) {
        return new UserResetPassword(
                userTestData.getCorporateEmail()
        );
    }

    public static UserResetPassword getUserResetPasswordFromUserEntity(@Valid UserEntity userEntity) {
        return new UserResetPassword(
                userEntity.getCorporateEmail()
        );
    }
}
