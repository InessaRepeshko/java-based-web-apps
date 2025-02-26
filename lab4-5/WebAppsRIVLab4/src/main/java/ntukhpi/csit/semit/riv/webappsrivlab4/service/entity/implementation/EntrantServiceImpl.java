package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.implementation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.repository.EntrantRepository;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidBirthday;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidRatingScore;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for managing `Entrant` entities.
 * This class provides methods for CRUD (Create, Read, Update, Delete) operations
 * and additional functionalities like filtering, sorting, and association handling.
 * <p>
 * Key functionalities:
 * - Fetching all entrants or filtered and sorted lists based on various criteria.
 * - Adding and updating entrants, with proper validation and exception handling.
 * - Deleting entrants while ensuring data integrity.
 * - Managing associations between entrants and students.
 * <p>
 * Exception handling:
 * - Validates input data and throws `CustomServiceException` for validation errors.
 * - Handles specific exceptions like `DataIntegrityViolationException` and `OptimisticLockingFailureException`
 * to manage database-related errors gracefully.
 * <p>
 * Dependencies:
 * - `EntrantRepository`: Provides data access methods for `Entrant` entities.
 * - `Validator` annotations: Ensures data consistency and validity at the service layer.
 * <p>
 * Transaction management:
 * - Annotated with `@Transactional` to ensure atomicity and rollback support for critical operations.
 *
 * @author Inessa Repeshko CS-222a
 * @see Entrant
 * @see EntrantRepository
 * @see CustomServiceException
 * @see Transactional
 * @see Valid
 * @see NotEmpty
 * @see DataIntegrityViolationException
 * @see EmptyResultDataAccessException
 * @see OptimisticLockingFailureException
 * @see ValidBirthday
 * @see ValidRatingScore
 * @see ValidId
 */

@Service
@Transactional
@Validated
public class EntrantServiceImpl implements EntrantService {
    private static final Logger logger = LoggerFactory.getLogger(EntrantServiceImpl.class);

    private final EntrantRepository entrantRepository;

    @Autowired
    public EntrantServiceImpl(EntrantRepository entrantRepository) {
        this.entrantRepository = entrantRepository;
    }

    @Override
    public List<Entrant> getAllEntrants() {
        return entrantRepository.findAll();
    }

    @Override
    public List<Entrant> getFilteredAndSortedEntrants(String search,
                                                      @ValidBirthday LocalDate birthdayStart,
                                                      @ValidBirthday LocalDate birthdayEnd,
                                                      @ValidRatingScore Double ratingScoreMin,
                                                      @ValidRatingScore Double ratingScoreMax,
                                                      String sort) {
        String sortPattern = "^(id|caseNumber|fullName|surname|name|patronymic|birthday|gender|ratingScore)-(asc|desc)$";

        if (!sort.trim().matches(sortPattern)) {
            String message = "Invalid sort field provided: " + sort + ". It should be in a format: 'fieldName-direction'.";
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
            case "caseNumber":
                sortOrders.add(new Sort.Order(sortDirection, "caseNumber"));
                sortOrders.add(new Sort.Order(sortDirection, "surname"));
                sortOrders.add(new Sort.Order(sortDirection, "name"));
                sortOrders.add(new Sort.Order(sortDirection, "patronymic"));
                break;

            case "surname":
            case "fullName":
                sortOrders.add(new Sort.Order(sortDirection, "surname"));
                sortOrders.add(new Sort.Order(sortDirection, "name"));
                sortOrders.add(new Sort.Order(sortDirection, "patronymic"));
                sortOrders.add(new Sort.Order(sortDirection, "caseNumber"));
                break;

            default:
                sortOrders.add(new Sort.Order(sortDirection, sortField));
                sortOrders.add(new Sort.Order(sortDirection, "surname"));
                sortOrders.add(new Sort.Order(sortDirection, "name"));
                sortOrders.add(new Sort.Order(sortDirection, "patronymic"));
                sortOrders.add(new Sort.Order(sortDirection, "caseNumber"));
                break;
        }

        Sort sortBy = Sort.by(sortOrders);

        try {
            return entrantRepository.findFilteredAndSortedEntrants(
                    search,
                    birthdayStart, birthdayEnd,
                    ratingScoreMin, ratingScoreMax,
                    sortBy);
        } catch (Exception e) {
            String message = "An error occurred while getting the filtered and sorted entrants. ";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message + e.getMessage(), e);
        }
    }

    @Override
    public List<Entrant> findEntrantsWithoutStudents() {
        List<Sort.Order> sortOrders = new ArrayList<>();

        sortOrders.add(new Sort.Order(Sort.Direction.ASC, "surname"));
        sortOrders.add(new Sort.Order(Sort.Direction.ASC, "name"));
        sortOrders.add(new Sort.Order(Sort.Direction.ASC, "patronymic"));
        sortOrders.add(new Sort.Order(Sort.Direction.ASC, "caseNumber"));

        Sort sort = Sort.by(sortOrders);

        return entrantRepository.findAllWithoutStudent(sort);
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            OptimisticLockingFailureException.class})
    public void saveAllEntrants(@NotEmpty(message = "The entrant list must not be null.") List<@Valid Entrant> entrants) {
        try {
            entrantRepository.saveAll(entrants);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception */
            String message = "Duplicate or invalid data detected.";
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
            String message = "An error occurred while saving the entities.";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message, e);
        }
    }

    @Override
    public Entrant findEntrantById(@ValidId Long id) {
        return entrantRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "No record exists with id: " + id + ".";
                    logger.error(errorMessage);
                    return new CustomServiceException(errorMessage);
                });
    }

    @Override
    public Entrant findEntrantByExample(@Valid Entrant entrant) {
        return entrantRepository.findBy(Example.of(entrant), query -> query.first().orElse(null));
    }

    @Override
    public Long findStudentIdForEntrant(@ValidId Long entrantId) {
        return entrantRepository.findStudentIdByEntrantId(entrantId).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            OptimisticLockingFailureException.class})
    public Entrant saveEntrant(@Valid Entrant entrant) {
        try {
            return entrantRepository.save(entrant);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception, foreign key, NOT NULL, CHECK, or length violation */
            String message = "The record with the case number '" + entrant.getCaseNumber() + "' already exists.";
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
            throw new CustomServiceException(message, e);
        }
    }

    @Override
    @Transactional
    public Entrant updateEntrant(@Valid Entrant entrant) {
        if (entrant.getId() == null) {
            String message = "Cannot update a record without an id.";
            logger.error(message);
            throw new CustomServiceException(message);
        }

        if (!entrantRepository.existsById(entrant.getId())) {
            String message = "No record exists to update.";
            logger.error(message);
            throw new CustomServiceException(message);
        }

        return saveEntrant(entrant);
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            EmptyResultDataAccessException.class,
            OptimisticLockingFailureException.class})
    public void deleteEntrantById(@ValidId Long id) {
        try {
            entrantRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            /* Handling data integrity violations, for example, when foreign keys are violated */
            String message = "Unable to delete the DAO due to data integrity constraints. ";
            logger.error(message + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (EmptyResultDataAccessException e) {
            /* Handling the case when an attempt to delete a non-existent record is made */
            String message = "The DAO does not exist and cannot be deleted.";
            logger.error("No DAO found for deletion: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling an error when trying to delete a record with a version conflict */
            String message = "The DAO could not be deleted due to a version conflict.";
            logger.error("Optimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An unexpected error occurred while deleting the DAO.";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message, e);
        }
    }
}
