package ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidName;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidPatronymic;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidSurname;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidPassword;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;

/**
 * Data Transfer Object (DTO) for user registration functionality.
 * This class encapsulates registration-related information for creating new user accounts.
 * <p>
 * Key functionalities:
 * - Represents the data required for user registration.
 * - Ensures validation of user input for secure and consistent registration processes.
 * <p>
 * Key attributes:
 * - `surname`: User's surname with validation for proper format.
 * - `name`: User's first name with validation for proper format.
 * - `patronymic`: User's patronymic with validation for proper format.
 * - `corporateEmail`: User's corporate email, which serves as a unique identifier.
 * - `username`: Username chosen by the user, validated for uniqueness and format.
 * - `password`: User's chosen password, validated for security standards.
 * - `confirmPassword`: Confirmation of the password, must match the main password.
 * <p>
 * Validation:
 * - `@ValidSurname`, `@ValidName`, `@ValidPatronymic`: Validate personal name components.
 * - `@ValidCorporateEmail`: Ensures the email follows a corporate format.
 * - `@ValidUsername`: Validates the username for proper format and length.
 * - `@ValidPassword`: Ensures the password meets defined security requirements.
 * <p>
 * Utility Methods:
 * - `getUserRegisterFromUserTestData`: Constructs a `UserRegister` object from `UserTestData` for testing or default data setup.
 * <p>
 * Relationships:
 * - Supports user registration by integrating with `UserTestData` and validation mechanisms.
 * - Forms the basis for creating `UserEntity` during user creation processes.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserTestData
 * @see ValidCorporateEmail
 * @see ValidPassword
 * @see ValidUsername
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {
    @ValidSurname
    private String surname;

    @ValidName
    private String name;

    @ValidPatronymic
    private String patronymic;

    @ValidCorporateEmail
    private String corporateEmail;

    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

    public static UserRegister getUserRegisterFromUserTestData(UserTestData userTestData) {
        return new UserRegister(
                userTestData.getSurname(),
                userTestData.getName(),
                userTestData.getPatronymic(),
                userTestData.getCorporateEmail(),
                userTestData.getUsername(),
                userTestData.getPassword(),
                userTestData.getPassword()
        );
    }
}
