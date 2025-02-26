package ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidName;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidPatronymic;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidSurname;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidPassword;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidRole;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;

/**
 * Data Transfer Object (DTO) for representing test data related to users.
 * This class encapsulates attributes required to simulate user data for testing and validation purposes.
 * <p>
 * Key functionalities:
 * - Provides a structured way to manage user data in testing scenarios.
 * - Ensures all user-related fields adhere to specified validation rules.
 * <p>
 * Key attributes:
 * - `username`: A unique identifier for the user, validated for proper format.
 * - `password`: The user's password, validated for security requirements.
 * - `role`: The user's role within the system, ensuring valid predefined roles.
 * - `corporateEmail`: The corporate email address, validated for proper formatting.
 * - `surname`: The user's last name, adhering to valid name conventions.
 * - `name`: The user's first name, validated for format and length.
 * - `patronymic`: The user's patronymic (middle name), ensuring valid format.
 * <p>
 * Validation:
 * - Ensures adherence to specific constraints using annotations:
 * - `@ValidUsername`, `@ValidPassword`, `@ValidRole`, `@ValidCorporateEmail`, `@ValidSurname`, `@ValidName`, `@ValidPatronymic`.
 * <p>
 * Relationships:
 * - Related to `Role` enum for defining user roles.
 * - Designed to work with other DTOs like `UserRegister`, `UserLogin`, and `UserForm` for comprehensive user management.
 *
 * @author Inessa Repeshko CS-222a
 * @see Role
 * @see ValidUsername
 * @see ValidPassword
 * @see ValidRole
 * @see ValidCorporateEmail
 * @see ValidSurname
 * @see ValidName
 * @see ValidPatronymic
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestData {
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidRole
    private Role role;

    @ValidCorporateEmail
    private String corporateEmail;

    @ValidSurname
    private String surname;

    @ValidName
    private String name;

    @ValidPatronymic
    private String patronymic;
}
