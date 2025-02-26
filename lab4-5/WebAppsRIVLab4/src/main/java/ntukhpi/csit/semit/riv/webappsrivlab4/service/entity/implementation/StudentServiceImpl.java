package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.implementation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.FundingType;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.ScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab4.repository.StudentRepository;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.StudentService;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;
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
 * Service implementation for managing `Student` entities.
 * This class provides methods for creating, updating, retrieving, and deleting students,
 * as well as filtering and sorting them based on various criteria.
 * <p>
 * Key functionalities:
 * - Retrieve all students or filtered/sorted lists of students.
 * - Save or update a single student or a batch of students with validation.
 * - Delete students while ensuring data integrity and exception handling.
 * - Handle relationships between `Student` and `Entrant` entities to maintain consistency.
 * <p>
 * Exception handling:
 * - Throws `CustomServiceException` for business logic errors or data-related exceptions.
 * - Handles specific exceptions like `DataIntegrityViolationException` and `ConstraintViolationException`
 * for robust error management.
 * <p>
 * Dependencies:
 * - `StudentRepository`: Provides data access methods for `Student` entities.
 * - `EntrantService`: Manages associated entrants and their relationships with students.
 * <p>
 * Transaction management:
 * - Annotated with `@Transactional` to ensure atomicity and rollback support for critical operations.
 *
 * @author Inessa Repeshko CS-222a
 * @see Student
 * @see StudentRepository
 * @see StudentService
 * @see EntrantService
 * @see Transactional
 * @see Valid
 * @see NotEmpty
 * @see ConstraintViolationException
 * @see DataIntegrityViolationException
 * @see EmptyResultDataAccessException
 * @see OptimisticLockingFailureException
 * @see CustomServiceException
 * @see FundingType
 * @see ScholarshipStatus
 * @see Logger
 * @see LoggerFactory
 */

@Service
@Transactional
@Validated
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final EntrantService entrantService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              EntrantService entrantService) {
        this.studentRepository = studentRepository;
        this.entrantService = entrantService;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            OptimisticLockingFailureException.class})
    public void saveAllStudents(@NotEmpty(message = "The student list must not be null.") List<@Valid Student> students) {
        try {
            students.forEach(student -> student.setEntrant(entrantService.findEntrantByExample(student.getEntrant())));
            studentRepository.saveAll(students);

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
    public Student findStudentById(@ValidId Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "No record exists with id: " + id + ".";
                    logger.error(errorMessage);
                    return new CustomServiceException(errorMessage);
                });
    }

    @Override
    public Student findStudentByExample(@Valid Student student) {
        return studentRepository.findBy(Example.of(student), query -> query.first().orElse(null));
    }

    @Override
    public List<Student> getFilteredAndSortedStudents(String search,
                                                      String fundingType,
                                                      String scholarshipStatus,
                                                      String sort) {
        String sortPattern = "^(id|caseNumber|fullName|surname|name|patronymic|fundingType|scholarshipStatus|corporateEmail)-(asc|desc)$";

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
                sortOrders.add(new Sort.Order(sortDirection, "entrant.caseNumber"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.surname"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.name"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.patronymic"));
                break;

            case "surname":
            case "fullName":
                sortOrders.add(new Sort.Order(sortDirection, "entrant.surname"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.name"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.patronymic"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.caseNumber"));
                break;

            default:
                sortOrders.add(new Sort.Order(sortDirection, sortField));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.surname"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.name"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.patronymic"));
                sortOrders.add(new Sort.Order(sortDirection, "entrant.caseNumber"));
                break;
        }

        Sort sortBy = Sort.by(sortOrders);

        String[] statuses = scholarshipStatus != null ? scholarshipStatus.trim().split("-") : new String[]{};
        List<ScholarshipStatus> scholarshipStatuses = new ArrayList<>();

        for (String status : statuses) {
            scholarshipStatuses.add(ScholarshipStatus.fromValue(status));
        }

        try {
            return studentRepository.findFilteredAndSortedStudents(
                    search,
                    fundingType != null ? FundingType.fromValue(fundingType) : null,
                    !scholarshipStatuses.isEmpty() ? scholarshipStatuses : null,
                    sortBy);
        } catch (Exception e) {
            String message = "An error occurred while getting the filtered and sorted students. ";
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
    public Student saveStudent(@Valid Student student) {
        try {
            return studentRepository.save(student);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception, foreign key, NOT NULL, CHECK, or length violation */
            String message = "The record with the corporate email '" + student.getCorporateEmail() + "' already exists.";
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
    public Student updateStudent(@Valid Student student) {
        if (student.getId() == null) {
            String message = "Cannot update a record without an id.";
            logger.error(message);
            throw new CustomServiceException(message);
        }

        if (!studentRepository.existsById(student.getId())) {
            String message = "No record exists to update.";
            logger.error(message);
            throw new CustomServiceException(message);
        }

        return saveStudent(student);
    }

    @Override
    @Transactional(rollbackFor = {
            CustomServiceException.class,
            DataIntegrityViolationException.class,
            EmptyResultDataAccessException.class,
            OptimisticLockingFailureException.class})
    public void deleteStudent(@ValidId Long id) {
        try {
            studentRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            /* Handling data integrity violations, for example, when foreign keys are violated */
            String message = "Unable to delete the DAO due to data integrity constraints.";
            logger.error(message + "\n" + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (EmptyResultDataAccessException e) {
            /* Handling the case when an attempt to delete a non-existent record is made */
            String message = "The DAO does not exist and cannot be deleted.";
            logger.error("No DAO found for deletion: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (OptimisticLockingFailureException e) {
            /* Handling an error when trying to delete a record with a version conflict */
            String message = "The DAO could not be deleted due to a version conflict.";
            logger.error(message + "\nOptimistic locking failure: " + e.getMessage());
            throw new CustomServiceException(message, e);

        } catch (Exception e) {
            /* General handling of other exceptions */
            String message = "An unexpected error occurred while deleting the DAO.";
            logger.error(message + e.getMessage());
            throw new CustomServiceException(message, e);
        }
    }
}
