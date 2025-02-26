package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.implementation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token.PasswordResetToken;
import ntukhpi.csit.semit.riv.webappsrivlab4.repository.PasswordResetTokenRepository;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.PasswordResetTokenService;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.ConstraintViolationException;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.LocalDateTime;

/**
 * Service implementation for managing `PasswordResetToken` entities.
 * This class provides methods for creating, retrieving, validating, and deleting password reset tokens.
 * <p>
 * Key functionalities:
 * - Find a token by its unique identifier or associated user ID.
 * - Save new tokens or update existing ones, ensuring data integrity and consistency.
 * - Validate tokens to check if they are associated with a user and not expired.
 * - Delete tokens by ID or associated user ID, with proper exception handling.
 * <p>
 * Exception handling:
 * - Throws `CustomServiceException` for business logic errors or data-related exceptions.
 * - Handles specific exceptions like `DataIntegrityViolationException` and `EmptyResultDataAccessException`
 * to provide meaningful error messages.
 * <p>
 * Dependencies:
 * - `PasswordResetTokenRepository`: Provides data access methods for `PasswordResetToken` entities.
 * - Validation annotations: Ensures proper data validation at the service layer.
 * <p>
 * Transaction management:
 * - Annotated with `@Transactional` to ensure atomicity and rollback support for critical operations.
 *
 * @author Inessa Repeshko CS-222a
 * @see PasswordResetToken
 * @see PasswordResetTokenRepository
 * @see CustomServiceException
 * @see Transactional
 * @see Valid
 * @see NotNull
 * @see ConstraintViolationException
 * @see DataIntegrityViolationException
 * @see EmptyResultDataAccessException
 * @see OptimisticLockingFailureException
 */

@Service
@Transactional
@Validated
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);

    private final PasswordResetTokenRepository tokenRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public PasswordResetToken findTokenByToken(@NotNull(message = "The UUID must not be null.") String UUID) {
        return tokenRepository.findByToken(UUID).orElseThrow(() -> {
            String message = "Token object not found for the passed token UUID.";
            logger.error(message);
            return new CustomServiceException(message);
        });
    }

    @Override
    public PasswordResetToken findTokenByUserId(@ValidId Long id) {
        return tokenRepository.findByUserId(id).orElseThrow(() -> {
            String message = "Token object not found for the passed user id.";
            logger.error(message);
            return new CustomServiceException(message);
        });
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            OptimisticLockingFailureException.class})
    public PasswordResetToken saveToken(@Valid PasswordResetToken token) {
        try {
            if (token.getUser() != null) {
                tokenRepository.findByUser(
                        token.getUser()
                ).ifPresent(
                        foundToken -> tokenRepository.delete(token)
                );
            }

            return tokenRepository.save(token);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception, foreign key, NOT NULL, CHECK, or length violation */
            String message = "The password reset token already exists.";
            logger.error(message + "\nData integrity violation: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (ConstraintViolationException e) {
            /* Handling violations of validation constraints */
            String message = "Validation failed for the password reset token.";
            logger.error(message + "\nConstraint violation: " + e.getMessage());
            throw new CustomServiceException(message + " Check the input data.", e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling version conflicts (if optimistic locking is used) */
            String message = "The password reset token could not be saved due to a version conflict.";
            logger.error(message + "\nOptimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An error occurred while saving the password reset token. ";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            EmptyResultDataAccessException.class,
            OptimisticLockingFailureException.class})
    public void deleteTokenByUserId(@ValidId Long id) {
        try {
            tokenRepository.deleteByUserId(id);

        } catch (DataIntegrityViolationException e) {
            /* Handling data integrity violations, for example, when foreign keys are violated */
            String message = "Unable to delete the password reset token due to data integrity constraints. ";
            logger.error(message + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (EmptyResultDataAccessException e) {
            /* Handling the case when an attempt to delete a non-existent record is made */
            String message = "The password reset token does not exist and cannot be deleted.";
            logger.error("No password reset token found for deletion: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling an error when trying to delete a record with a version conflict */
            String message = "The password reset token could not be deleted due to a version conflict.";
            logger.error("Optimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An unexpected error occurred while deleting the password reset token.";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            EmptyResultDataAccessException.class,
            OptimisticLockingFailureException.class})
    public void deleteTokenById(@ValidId Long id) {
        try {
            tokenRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            /* Handling data integrity violations, for example, when foreign keys are violated */
            String message = "Unable to delete the password reset token due to data integrity constraints. ";
            logger.error(message + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (EmptyResultDataAccessException e) {
            /* Handling the case when an attempt to delete a non-existent record is made */
            String message = "The password reset token does not exist and cannot be deleted.";
            logger.error("No password reset token found for deletion: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling an error when trying to delete a record with a version conflict */
            String message = "The password reset token could not be deleted due to a version conflict.";
            logger.error("Optimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An unexpected error occurred while deleting the password reset token.";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message, e);
        }
    }

    @Override
    public boolean isValidToken(@Valid PasswordResetToken passwordResetToken) {
        return isTokenContainsUserData(passwordResetToken) && !isTokenExpired(passwordResetToken);
    }

    @Override
    public boolean isTokenContainsUserData(@Valid PasswordResetToken token) {
        return token != null && token.getUser() != null && token.getUser().getId() != null;
    }

    @Override
    public boolean isTokenExpired(@Valid PasswordResetToken token) {
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(token.getExpiryDate());
    }
}
