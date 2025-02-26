package ntukhpi.csit.semit.riv.webappsrivlab3.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab3.repository.EntrantRepository;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.EntrantService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntrantServiceImpl implements EntrantService {
    private static final Logger logger = LoggerFactory.getLogger(EntrantServiceImpl.class);

    private final EntrantRepository entrantRepository;

    public EntrantServiceImpl(EntrantRepository entrantRepository) {
        super();
        this.entrantRepository = entrantRepository;
    }

    @Override
    public List<Entrant> getAllEntrants() {
        return entrantRepository.findAll();
    }

    @Override
    public List<Entrant> getFilteredAndSortedEntrants(String search,
                                                      LocalDate birthdayStart,
                                                      LocalDate birthdayEnd,
                                                      Double ratingScoreMin,
                                                      Double ratingScoreMax,
                                                      String sort) {
        String sortPattern = "^(id|caseNumber|fullName|surname|name|patronymic|birthday|gender|ratingScore)-(asc|desc)$";

        if (!sort.trim().matches(sortPattern)) {
            logger.warn("Invalid sort field provided: " + sort + ". It should be in a format: 'fieldName-direction'.");
            throw new IllegalArgumentException("Invalid sort field provided: " + sort + ". It should be in a format: 'fieldName-direction'.");
        }

        String[] sortParams = sort.split("-");
        String sortField = sortParams[0];
        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction.fromString(sortParams[1]);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
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
            logger.error("An error occurred while getting the filtered and sorted entrants: " + e.getMessage());
            throw new RuntimeException("An error occurred while getting the filtered and sorted entrants: " + e.getMessage());
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
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, OptimisticLockingFailureException.class})
    public void saveAllEntrants(List<Entrant> entrants) {
        try {
            entrantRepository.saveAll(entrants);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception */
            logger.error("Duplicate or invalid data detected.\nData integrity violation: " + e.getMessage());
            throw new IllegalArgumentException("Duplicate or invalid data detected.");

        } catch (ConstraintViolationException e) {
            /* Handling violations of validation constraints */
            logger.error("Validation failed for the entity.\nConstraint violation: " + e.getMessage());
            throw new IllegalArgumentException("Validation failed for the entity. Check the input data.");

        } catch (OptimisticLockingFailureException e) {
            /* Handling version conflicts (if optimistic locking is used) */
            logger.error("Optimistic locking failure: " + e.getMessage());
            throw new RuntimeException("The entity could not be saved due to a version conflict.");

        } catch (Exception e) {
            /* General handling of other exceptions */
            logger.error("An error occurred while saving the entity.\n" + e.getMessage());
            throw new RuntimeException("An error occurred while saving the entity.");
        }
    }

    @Override
    public Entrant findEntrantById(Long id) {
        return entrantRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "No record exists with id: " + id + ".";
                    logger.error(errorMessage);
                    return new EntityNotFoundException(errorMessage);
                });
    }

    @Override
    public Entrant findEntrantByExample(Entrant entrant) {
        Example<Entrant> example = Example.of(entrant);
        return entrantRepository.findBy(example, query -> query.first().orElse(null));
    }

    @Override
    public Long findStudentIdForEntrant(Long entrantId) {
        return entrantRepository.findStudentIdByEntrantId(entrantId).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, OptimisticLockingFailureException.class})
    public Entrant saveEntrant(Entrant entrant) {
        try {
            return entrantRepository.save(entrant);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception, foreign key, NOT NULL, CHECK, or length violation */
            logger.error("Duplicate or invalid data detected.\nData integrity violation: " + e.getMessage());
            throw new IllegalArgumentException(
                    "The record with the case number '" + entrant.getCaseNumber() + "' already exists.");

        } catch (ConstraintViolationException e) {
            /* Handling violations of validation constraints */
            logger.error("Validation failed for the entity.\nConstraint violation: " + e.getMessage());
            throw new IllegalArgumentException("Validation failed for the entity. Check the input data.");

        } catch (OptimisticLockingFailureException e) {
            /* Handling version conflicts (if optimistic locking is used) */
            logger.error("Optimistic locking failure: " + e.getMessage());
            throw new RuntimeException("The entity could not be saved due to a version conflict.");

        } catch (Exception e) {
            /* General handling of other exceptions */
            logger.error("An error occurred while saving the entity.\n" + e.getMessage());
            throw new RuntimeException("An error occurred while saving the entity.");
        }
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, OptimisticLockingFailureException.class})
    public Entrant updateEntrant(Entrant entrant) {
        if (entrant.getId() == null) {
            logger.error("Cannot update a record without an id.");
            throw new IllegalArgumentException("Cannot update a record without an id.");
        }

        if (!entrantRepository.existsById(entrant.getId())) {
            logger.error("No record exists to update.");
            throw new IllegalArgumentException("No record exists to update.");
        }

        return saveEntrant(entrant);
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, EmptyResultDataAccessException.class, OptimisticLockingFailureException.class})
    public void deleteEntrant(Entrant entrant) {
        try {
            entrantRepository.delete(entrant);

        } catch (DataIntegrityViolationException e) {
            /* Handling data integrity violations, for example, when foreign keys are violated */
            logger.error("Data integrity violation occurred: " + e.getMessage());
            throw new IllegalArgumentException("Unable to delete the entity due to data integrity constraints.");

        } catch (EmptyResultDataAccessException e) {
            /* Handling the case when an attempt to delete a non-existent record is made */
            logger.error("No entity found for deletion: " + e.getMessage());
            throw new IllegalArgumentException("The entity does not exist and cannot be deleted.");

        } catch (OptimisticLockingFailureException e) {
            /* Handling an error when trying to delete a record with a version conflict */
            logger.error("Optimistic locking failure: " + e.getMessage());
            throw new IllegalArgumentException("The entity could not be deleted due to a version conflict.");

        } catch (Exception e) {
            /* General handling of other exceptions */
            logger.error("An unexpected error occurred while deleting the entity: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while deleting the entity.");
        }
    }
}
