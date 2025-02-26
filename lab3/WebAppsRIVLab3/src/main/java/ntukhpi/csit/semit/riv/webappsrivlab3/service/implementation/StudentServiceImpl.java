package ntukhpi.csit.semit.riv.webappsrivlab3.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.FundingType;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.ScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab3.repository.StudentRepository;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.EntrantService;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final EntrantService entrantService;

    public StudentServiceImpl(StudentRepository studentRepository, EntrantService entrantService) {
        super();
        this.studentRepository = studentRepository;
        this.entrantService = entrantService;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, OptimisticLockingFailureException.class})
    public void saveAllStudents(List<Student> students) {
        try {
            students.forEach(student -> student.setEntrant(entrantService.findEntrantByExample(student.getEntrant())));
            studentRepository.saveAll(students);

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
    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "No record exists with id: " + id + ".";
                    logger.error(errorMessage);
                    return new EntityNotFoundException(errorMessage);
                });
    }

    @Override
    public Student findStudentByExample(Student student) {
        Example<Student> example = Example.of(student);
        return studentRepository.findBy(example, query -> query.first().orElse(null));
    }

    @Override
    public List<Student> getFilteredAndSortedStudents(String search,
                                                      String fundingType,
                                                      String scholarshipStatus,
                                                      String sort) {
        String sortPattern = "^(id|caseNumber|fullName|surname|name|patronymic|fundingType|scholarshipStatus|corporateEmail)-(asc|desc)$";

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
            logger.error("An error occurred while getting the filtered and sorted students: " + e.getMessage());
            throw new RuntimeException("An error occurred while getting the filtered and sorted students: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, OptimisticLockingFailureException.class})
    public Student saveStudent(Student student) {
        try {
            return studentRepository.save(student);

        } catch (DataIntegrityViolationException e) {
            /* Handling a duplicate or uniqueness exception, foreign key, NOT NULL, CHECK, or length violation */
            logger.error("Duplicate or invalid data detected.\nData integrity violation: " + e.getMessage());
            throw new IllegalArgumentException(
                    "The record with the corporate email '" + student.getCorporateEmail() + "' already exists.");

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
            throw new RuntimeException("An error occurred while saving the entity." +  e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, OptimisticLockingFailureException.class})
    public Student updateStudent(Student student) {
        if (student.getId() == null) {
            logger.error("Cannot update a record without an id.");
            throw new IllegalArgumentException("Cannot update a record without an id.");
        }

        if (!studentRepository.existsById(student.getId())) {
            logger.error("No record exists to update.");
            throw new IllegalArgumentException("No record exists to update.");
        }

        return saveStudent(student);
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, EmptyResultDataAccessException.class, OptimisticLockingFailureException.class})
    public void deleteStudent(Student student) {
        try {
            studentRepository.delete(student);

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
