package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.implementation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import ntukhpi.csit.semit.riv.webappsrivlab4.config.EncoderConfig;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.repository.PasswordResetTokenRepository;
import ntukhpi.csit.semit.riv.webappsrivlab4.repository.UserRepository;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidRole;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsernameOrCorporateEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for managing `UserEntity` entities.
 * This class provides methods for creating, updating, retrieving, and deleting users,
 * as well as filtering and sorting users based on various criteria.
 * <p>
 * Key functionalities:
 * - Retrieve all users or filtered/sorted lists of users.
 * - Save or update a single user or a batch of users with validation.
 * - Delete users while maintaining data integrity and exception handling.
 * - Handle relationships between users and other entities like `PasswordResetToken`.
 * <p>
 * Exception handling:
 * - Throws `CustomServiceException` for business logic errors or data-related exceptions.
 * - Handles specific exceptions like `DataIntegrityViolationException` and `ConstraintViolationException`
 * for robust error management.
 * <p>
 * Dependencies:
 * - `UserRepository`: Provides data access methods for `UserEntity` entities.
 * - `PasswordResetTokenRepository`: Manages password reset tokens for user accounts.
 * - `EncoderConfig`: Handles password encoding and security configurations.
 * <p>
 * Transaction management:
 * - Annotated with `@Transactional` to ensure atomicity and rollback support for critical operations.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see UserRepository
 * @see PasswordResetTokenRepository
 * @see UserService
 * @see EncoderConfig
 * @see Transactional
 * @see Valid
 * @see ConstraintViolationException
 * @see DataIntegrityViolationException
 * @see EmptyResultDataAccessException
 * @see OptimisticLockingFailureException
 * @see CustomServiceException
 * @see ValidId
 * @see ValidUsername
 * @see ValidCorporateEmail
 * @see ValidUsernameOrCorporateEmail
 * @see Role
 * @see Logger
 * @see LoggerFactory
 */

@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final EncoderConfig encoderConfig;
    private final PasswordResetTokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EncoderConfig encoderConfig,
                           PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.encoderConfig = encoderConfig;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> findUsersByRole(@ValidRole Role role) {
        return userRepository.findUsersByRole(role);
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            OptimisticLockingFailureException.class})
    public void saveAllUsers(@NotEmpty(message = "The user list must not be null.") List<UserEntity> userEntities) {
        try {
            userEntities.forEach(user -> user.setPassword(encoderConfig.passwordEncoder().encode(user.getPassword())));
            userRepository.saveAll(userEntities);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception */
            String message = "Duplicate or invalid data detected. ";
            logger.error(message + "\nData integrity violation: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (ConstraintViolationException e) {
            /* Handling violations of validation constraints */
            String message = "Validation failed for the DAO.";
            logger.error(message + "\nConstraint violation: " + e.getMessage());
            throw new CustomServiceException(message + " Check the input data.", e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling version conflicts (if optimistic locking is used) */
            String message = "The DAO could not be saved due to a version conflict.";
            logger.error(message + "\nOptimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An error occurred while saving the DAO.";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message, e);
        }
    }

    @Override
    public UserEntity findUserById(@ValidId Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "No record exists with id: " + id + ".";
                    logger.error(errorMessage);
                    return new CustomServiceException(errorMessage);
                });
    }

    @Override
    public UserEntity findUserByUsername(@ValidUsername String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String message = "No user found for passed username.";
            logger.error(message);
            return new CustomServiceException(message);
        });
    }

    @Override
    public UserEntity findUserByCorporateEmail(@ValidCorporateEmail String corporateEmail) {
        return userRepository.findByCorporateEmail(corporateEmail).orElseThrow(() -> {
            String message = "No user found for passed corporate email.";
            logger.error(message);
            return new CustomServiceException(message);
        });
    }

    @Override
    public String findPasswordByUserId(@ValidId Long id) {
        return userRepository.findById(id)
                .map(UserEntity::getPassword)
                .orElseThrow(() -> {
                    String errorMessage = "No record exists with id: " + id + ".";
                    logger.error(errorMessage);
                    return new CustomServiceException(errorMessage);
                });
    }

    @Override
    public String findPasswordByUserCorporateEmail(@ValidCorporateEmail String corporateEmail) {
        return userRepository.findByCorporateEmail(corporateEmail)
                .map(UserEntity::getPassword)
                .orElseThrow(() -> {
                    String errorMessage = "No record exists with corporate email: " + corporateEmail + ".";
                    logger.error(errorMessage);
                    return new CustomServiceException(errorMessage);
                });
    }

    @Override
    public UserEntity findUserByUsernameOrCorporateEmail(@ValidUsernameOrCorporateEmail String usernameOrCorporateEmail) {
        return userRepository.findByUsernameOrCorporateEmail(
                        usernameOrCorporateEmail,
                        usernameOrCorporateEmail)
                .orElseThrow(() -> {
                    String message = "No user found for passed user identifier (username or corporate email).";
                    logger.error(message);
                    return new CustomServiceException(message);
                });
    }

    @Override
    public UserEntity findUserByExample(@Valid UserEntity userEntity) {
        return userRepository.findBy(Example.of(userEntity), query -> query.first().orElse(null));
    }

    @Override
    public List<UserEntity> getFilteredAndSortedUsers(String search,
                                                      String role,
                                                      String sort) {
        String sortPattern = "^(id|username|role|corporateEmail|fullName|surname|name|patronymic)-(asc|desc)$";

        if (!sort.trim().matches(sortPattern)) {
            String message = "Invalid sort field provided: " + sort +
                    ". It should be in a format: 'fieldName-direction'.";
            logger.warn(message);
            throw new CustomServiceException(message);
        }

        String[] sortParams = sort.split("-");
        String sortField = sortParams[0];
        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction.fromString(sortParams[1]);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new CustomServiceException(e.getMessage(), e);
        }

        List<Sort.Order> sortOrders = new ArrayList<>();

        switch (sortField) {
            case "surname":
            case "fullName":
                sortOrders.add(new Sort.Order(sortDirection, "surname"));
                sortOrders.add(new Sort.Order(sortDirection, "name"));
                sortOrders.add(new Sort.Order(sortDirection, "patronymic"));
                break;

            default:
                sortOrders.add(new Sort.Order(sortDirection, sortField));
                sortOrders.add(new Sort.Order(sortDirection, "surname"));
                sortOrders.add(new Sort.Order(sortDirection, "name"));
                sortOrders.add(new Sort.Order(sortDirection, "patronymic"));
                break;
        }

        Sort sortBy = Sort.by(sortOrders);

        String[] roles = role != null ? role.trim().split("-") : new String[]{};
        List<Role> roleList = new ArrayList<>();

        for (String roleName : roles) {
            roleList.add(Role.fromName(roleName));
        }

        try {
            return userRepository.findFilteredAndSortedUsers(
                    search,
                    !roleList.isEmpty() ? roleList : null,
                    sortBy);
        } catch (Exception e) {
            String message = "An error occurred while getting the filtered and sorted users. ";
            logger.error(message + e.getMessage());
            throw new CustomServiceException(message + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            OptimisticLockingFailureException.class})
    public UserEntity saveUser(@Valid UserEntity userEntity) {
        try {
            if (userEntity.getPassword() != null && userEntity.getPassword().length() < 60) {
                userEntity.setPassword(encoderConfig.passwordEncoder().encode(userEntity.getPassword()));
            }

            return userRepository.save(userEntity);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception, foreign key, NOT NULL, CHECK, or length violation */
            String message = "The record with the username '" + userEntity.getUsername()
                    + "' or corporate email '" + userEntity.getCorporateEmail() + "' already exists.";
            logger.error(message + "\nData integrity violation: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (ConstraintViolationException e) {
            /* Handling violations of validation constraints */
            String message = "Validation failed for the DAO.";
            logger.error(message + "\nConstraint violation: " + e.getMessage());
            throw new CustomServiceException(message + " Check the input data.", e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling version conflicts (if optimistic locking is used) */
            String message = "The DAO could not be saved due to a version conflict.";
            logger.error(message + "\nOptimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An error occurred while saving the DAO. ";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public UserEntity updateUser(@Valid UserEntity userEntity) {
        if (userEntity.getId() == null) {
            String message = "Cannot update a record without an id.";
            logger.error(message);
            throw new CustomServiceException(message);
        }

        if (!userRepository.existsById(userEntity.getId())) {
            String message = "No record exists to update.";
            logger.error(message);
            throw new CustomServiceException(message);
        }

        if (userEntity.getPassword() == null) {
            userEntity.setPassword(userRepository.findById(userEntity.getId()).get().getPassword());
        }

        return saveUser(userEntity);
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            EmptyResultDataAccessException.class,
            OptimisticLockingFailureException.class})
    public void deleteUserById(@ValidId Long id) {
        try {
            tokenRepository.deleteByUserId(id);
            userRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            /* Handling data integrity violations, for example, when foreign keys are violated */
            String message = "Unable to delete the user due to data integrity constraints. ";
            logger.error(message + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (EmptyResultDataAccessException e) {
            /* Handling the case when an attempt to delete a non-existent record is made */
            String message = "The user does not exist and cannot be deleted.";
            logger.error("No user found for deletion: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling an error when trying to delete a record with a version conflict */
            String message = "The user could not be deleted due to a version conflict.";
            logger.error("Optimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An unexpected error occurred while deleting the user.";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message, e);
        }
    }
}
