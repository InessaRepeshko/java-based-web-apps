package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token.PasswordResetToken;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;

/**
 * Service interface for managing `PasswordResetToken` entities.
 * This interface provides methods for creating, retrieving, validating, and deleting password reset tokens.
 * It ensures that tokens are managed securely and efficiently for the purpose of user authentication and password recovery.
 * <p>
 * Key functionalities:
 * - Find password reset tokens by token value or user ID.
 * - Save new tokens or update existing ones.
 * - Validate tokens for expiration and associated user data.
 * - Delete tokens by user ID or token ID.
 * <p>
 * Validation:
 * - Enforces input validation using annotations like `@Valid`, `@ValidId`, and `@NotNull`.
 * - Ensures compliance with domain-specific constraints to maintain data integrity.
 * <p>
 * Dependencies:
 * - Annotated parameters enforce constraints for safer data handling and consistent method behavior.
 * - Relies on custom validators for ID and token validation.
 *
 * @author Inessa Repeshko CS-222a
 * @see PasswordResetToken
 * @see ValidId
 * @see Valid
 * @see NotNull
 */

public interface PasswordResetTokenService {
    PasswordResetToken findTokenByToken(@NotNull(message = "The UUID must not be null.") String token);

    PasswordResetToken findTokenByUserId(@ValidId Long id);

    PasswordResetToken saveToken(@Valid PasswordResetToken token);

    void deleteTokenByUserId(@ValidId Long id);

    void deleteTokenById(@ValidId Long id);

    boolean isValidToken(@Valid PasswordResetToken passwordResetToken);

    boolean isTokenContainsUserData(@Valid PasswordResetToken passToken);

    boolean isTokenExpired(@Valid PasswordResetToken passToken);
}
