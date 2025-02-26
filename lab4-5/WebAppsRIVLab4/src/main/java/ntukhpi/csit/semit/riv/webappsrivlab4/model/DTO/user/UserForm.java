package ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.*;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidRole;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;

import java.util.Arrays;
import java.util.List;

/**
 * Data Transfer Object (DTO) for handling user data in forms.
 * This class encapsulates user-related information for registration, updates, or display in forms.
 * <p>
 * Key functionalities:
 * - Maps user entity data into a form-friendly structure.
 * - Ensures validation for user attributes like name, username, email, and role.
 * - Provides utility methods for formatting field names and converting entities into DTOs.
 * <p>
 * Key attributes:
 * - `id`: Unique identifier for the user.
 * - `surname`, `name`, `patronymic`: Personal details of the user.
 * - `corporateEmail`: Corporate email associated with the user.
 * - `username`: Username for authentication.
 * - `role`: Role assigned to the user in the system.
 * <p>
 * Validation:
 * - Includes various custom validators like `@ValidSurname`, `@ValidName`, `@ValidUsername`, `@ValidCorporateEmail`, and `@ValidRole` to enforce data integrity.
 * <p>
 * Relationships:
 * - Tightly coupled with `UserEntity` for transformations between entity and DTO.
 * - Integrated into processes like user registration and profile management.
 * <p>
 * Utility Methods:
 * - `getUserFormFromUserEntity`: Converts a `UserEntity` instance into a `UserForm`.
 * - `getFieldNamesAsFormattedStrings`: Returns a list of formatted field names for use in UI.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see Role
 * @see ValidSurname
 * @see ValidName
 * @see ValidUsername
 * @see ValidCorporateEmail
 * @see ValidRole
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private Long id;

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

    @ValidRole
    private Role role;

    public static UserForm getUserFormFromUserEntity(@Valid UserEntity userEntity) {
        return new UserForm(
                userEntity.getId(),
                userEntity.getSurname(),
                userEntity.getName(),
                userEntity.getPatronymic(),
                userEntity.getCorporateEmail(),
                userEntity.getUsername(),
                userEntity.getRole() != null ? userEntity.getRole() : Role.STUDENT_VIEWER
        );
    }

    public static String[][] getFieldNamesAsFormattedStrings() {
        List<String[]> fieldNames = Arrays.asList(
                new String[]{"fullName", "Full Name"},
                new String[]{"role", "Role"},
                new String[]{"username", "Username"},
                new String[]{"corporateEmail", "Corporate Email"}
        );

        return fieldNames.toArray(String[][]::new);
    }
}
