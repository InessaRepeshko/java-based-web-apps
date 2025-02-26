package ntukhpi.csit.semit.riv.webappsrivlab4.repository;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `UserEntity` entities in the database.
 * Provides CRUD operations and custom queries for filtering and sorting user data.
 * <p>
 * Key functionalities:
 * - Extends `JpaRepository` to enable basic CRUD operations.
 * - Includes custom methods for searching users by username, email, or roles.
 * - Implements a custom query for filtering and sorting users based on search criteria and roles.
 * <p>
 * Key methods:
 * - `findByUsername`: Fetches a user entity by its unique username.
 * - `findByCorporateEmail`: Fetches a user entity by its unique corporate email.
 * - `findByUsernameOrCorporateEmail`: Fetches a user entity using either its username or corporate email.
 * - `existsUserByUsernameOrCorporateEmail`: Checks the existence of a user by username or corporate email.
 * - `findUsersByRole`: Retrieves all users with a specific role.
 * - `findFilteredAndSortedUsers`: Filters and sorts users based on a search term and roles.
 * <p>
 * Query Parameters:
 * - `search`: Filters users by concatenated full name (surname, name, and patronymic) containing the search term.
 * - `roles`: Filters users by their roles (e.g., ADMIN, STUDENT_VIEWER).
 * - `sortBy`: Specifies sorting order for the query results.
 * <p>
 * Relationships:
 * - Works with the `UserEntity` class to represent users.
 * - Utilizes the `Role` enum for filtering by user roles.
 * <p>
 * Usage:
 * - Designed for use within the service layer to fetch and manage user data efficiently.
 * - Facilitates user management with flexible search and filtering capabilities.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see Role
 * @see JpaRepository
 * @see Sort
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByCorporateEmail(String corporateEmail);

    Optional<UserEntity> findByUsernameOrCorporateEmail(String username, String corporateEmail);

    List<UserEntity> findUsersByRole(Role role);

    @Query("SELECT u FROM UserEntity u "
            + "WHERE (:search IS NULL OR UPPER(CONCAT(u.surname, ' ', u.name, ' ', u.patronymic)) LIKE UPPER(CONCAT(:search, '%'))) "
            + "AND (:roles IS NULL OR u.role IN :roles)")
    List<UserEntity> findFilteredAndSortedUsers(@Param("search") String search,
                                                @Param("roles") List<Role> roles,
                                                Sort sortBy);
}
