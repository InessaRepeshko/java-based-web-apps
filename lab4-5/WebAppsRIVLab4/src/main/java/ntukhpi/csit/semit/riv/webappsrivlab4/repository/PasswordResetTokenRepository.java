package ntukhpi.csit.semit.riv.webappsrivlab4.repository;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token.PasswordResetToken;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing `PasswordResetToken` entities in the database.
 * Provides CRUD operations and custom queries for handling password reset tokens.
 * <p>
 * Key functionalities:
 * - Extends `JpaRepository` to provide basic CRUD operations.
 * - Implements methods for finding and deleting password reset tokens based on specific criteria.
 * <p>
 * Key methods:
 * - `findByToken`: Retrieves a password reset token by its unique token string.
 * - `findByUserId`: Finds a password reset token associated with a specific user ID.
 * - `findByUser`: Fetches a password reset token for a given `UserEntity`.
 * - `deleteByUserId`: Deletes a password reset token associated with a specific user ID.
 * <p>
 * Relationships:
 * - Directly interacts with the `PasswordResetToken` entity.
 * - Associated with the `UserEntity` to link tokens to users.
 * <p>
 * Usage:
 * - Designed to be used within the service layer for managing password reset workflows.
 * - Enables secure and efficient handling of password reset processes.
 *
 * @author Inessa Repeshko CS-222a
 * @see PasswordResetToken
 * @see UserEntity
 * @see JpaRepository
 */

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUserId(Long userId);

    Optional<PasswordResetToken> findByUser(UserEntity user);

    void deleteByUserId(Long userId);
}
