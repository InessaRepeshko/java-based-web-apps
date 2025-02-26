package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.UserForm;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.UserRegister;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.UserTestData;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.RoleConverter;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidName;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidPatronymic;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidSurname;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidEncodedPassword;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidRole;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

/**
 * Entity class representing a user in the system.
 * This class maps to the "users" table in the database and contains user-specific information, including
 * authentication and authorization details.
 * <p>
 * Key functionalities:
 * - Represents user attributes such as username, password, role, email, and personal details (surname, name, patronymic).
 * - Provides factory methods to create `UserEntity` instances from DTO objects (`UserForm`, `UserRegister`, and `UserTestData`).
 * - Includes validations for username, email, and personal details to ensure data integrity.
 * <p>
 * Key attributes:
 * - `id`: Unique identifier for the user.
 * - `username`: Unique username for the user.
 * - `password`: Encrypted password for authentication.
 * - `role`: Role of the user, defining access levels.
 * - `corporateEmail`: Corporate email associated with the user.
 * - `surname`, `name`, `patronymic`: Personal details of the user.
 * <p>
 * Relationships:
 * - Utilizes the `Role` enum for role-based access control.
 * - Supports integration with DTO classes (`UserForm`, `UserRegister`, `UserTestData`) for data transfer.
 * <p>
 * Constraints:
 * - Username must be unique and follow a specific pattern.
 * - Email must match the corporate email format and be unique.
 * - Personal details have length and format constraints.
 *
 * @author Inessa Repeshko CS-222a
 * @see Role
 * @see RoleConverter
 * @see UserForm
 * @see UserRegister
 * @see UserTestData
 */

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Positive(message = "The id must be a positive number.")
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 32)
    @Check(constraints = "REGEXP_LIKE(username, '" + ValidationConstants.REGEX_USERNAME + "', 'c') = 1")
    @Length(min = 8, max = 32, message = "The username should be between 8 to 32 characters.")
    @ValidUsername
    private String username;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = RoleConverter.class)
    @ColumnDefault(value = "'STUDENT_VIEWER'")
    @ValidRole
    private Role role;

    @Column(name = "corporate_email", nullable = false, unique = true, length = 123)
    @Check(constraints = "REGEXP_LIKE(corporate_email, '" + ValidationConstants.REGEX_CORPORATE_EMAIL + "', 'c') = 1")
    @Length(min = 17, max = 123, message = "The corporate email length should be between 1 to 50 characters.")
    @org.hibernate.validator.constraints.Email(regexp = ValidationConstants.REGEX_CORPORATE_EMAIL, message = "The corporate email should match the format 'name.surname@faculty.khpi.edu.ua'.")
    @ValidCorporateEmail
    private String corporateEmail;

    @Column(name = "surname", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(surname, '" + ValidationConstants.REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The surname length should be between 1 to 50 characters.")
    @ValidSurname
    private String surname;

    @Column(name = "name", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(name, '" + ValidationConstants.REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The name length should be between 1 to 50 characters.")
    @ValidName
    private String name;

    @Column(name = "patronymic", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(patronymic, '" + ValidationConstants.REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The patronymic length should be between 1 to 50 characters.")
    @ValidPatronymic
    private String patronymic;

    public UserEntity(String username, String password, Role role, String corporateEmail, String surname, String name, String patronymic) {
        setUsername(username);
        setPassword(password);
        setRole(role);
        setCorporateEmail(corporateEmail);
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);
    }

    public UserEntity(String username, Role role, String corporateEmail, String surname, String name, String patronymic) {
        setUsername(username);
        setRole(role);
        setCorporateEmail(corporateEmail);
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);
    }

    public static UserEntity getUserEntityFromUserTestData(@Valid UserTestData userTestData) {
        return new UserEntity(
                null,
                userTestData.getUsername(),
                userTestData.getPassword(),
                userTestData.getRole(),
                userTestData.getCorporateEmail(),
                userTestData.getSurname(),
                userTestData.getName(),
                userTestData.getPatronymic()
        );
    }

    public static UserEntity getUserEntityFromUserRegister(@Valid UserRegister userRegister) {
        return new UserEntity(
                null,
                userRegister.getUsername(),
                userRegister.getPassword(),
                Role.STUDENT_VIEWER,
                userRegister.getCorporateEmail(),
                userRegister.getSurname(),
                userRegister.getName(),
                userRegister.getPatronymic()
        );
    }

    public static UserEntity getUserEntityFromUserForm(@Valid UserForm userForm,
                                                       @ValidEncodedPassword String password) {
        return new UserEntity(
                userForm.getId(),
                userForm.getUsername(),
                password,
                userForm.getRole() != null ? userForm.getRole() : Role.STUDENT_VIEWER,
                userForm.getCorporateEmail(),
                userForm.getSurname(),
                userForm.getName(),
                userForm.getPatronymic()
        );
    }
}
