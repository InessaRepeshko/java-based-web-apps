package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidRole;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsernameOrCorporateEmail;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Service interface for managing `UserEntity` entities.
 * This interface provides methods for CRUD operations, filtering, sorting, and retrieving user data.
 * It ensures proper handling of user records with validation and compliance to business rules.
 * <p>
 * Key functionalities:
 * - Retrieve all users or filter and sort based on role and search criteria.
 * - Save multiple user records in bulk.
 * - Find users by their unique ID, username, corporate email, or example criteria.
 * - Create new user records or update existing ones.
 * - Delete users by their ID.
 * - Retrieve user passwords securely by their ID or corporate email.
 * <p>
 * Validation:
 * - Enforces input validation using annotations like `@Valid`, `@NotEmpty`, and `@ValidId`.
 * - Ensures compliance with domain-specific constraints for roles, IDs, and other parameters.
 * - Relies on custom validators for enhanced validation rules, such as `ValidUsername`, `ValidCorporateEmail`, and `ValidRole`.
 * <p>
 * Dependencies:
 * - Provides consistent validation and error handling through annotated parameters.
 * - Relies on domain objects like `UserEntity` and `Role` to encapsulate user data.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see Role
 * @see ValidId
 * @see ValidUsername
 * @see ValidCorporateEmail
 * @see ValidRole
 * @see ValidUsernameOrCorporateEmail
 * @see Valid
 * @see NotEmpty
 */

@Validated
public interface UserService {
    List<UserEntity> getAllUsers();

    List<UserEntity> findUsersByRole(@ValidRole Role role);

    void saveAllUsers(@NotEmpty(message = "The user list must not be null.") List<UserEntity> userEntities);

    UserEntity findUserById(@ValidId Long id);

    UserEntity findUserByExample(@Valid UserEntity userEntity);

    UserEntity findUserByUsername(@ValidUsername String username);

    UserEntity findUserByCorporateEmail(@ValidCorporateEmail String CorporateEmail);

    String findPasswordByUserId(@ValidId Long id);

    String findPasswordByUserCorporateEmail(@ValidCorporateEmail String corporateEmail);

    UserEntity findUserByUsernameOrCorporateEmail(@ValidUsernameOrCorporateEmail String usernameOrCorporateEmail);

    List<UserEntity> getFilteredAndSortedUsers(String search,
                                               String role,
                                               String sort);

    UserEntity saveUser(@Valid UserEntity userEntity);

    UserEntity updateUser(@Valid UserEntity userEntity);

    void deleteUserById(@ValidId Long id);
}
