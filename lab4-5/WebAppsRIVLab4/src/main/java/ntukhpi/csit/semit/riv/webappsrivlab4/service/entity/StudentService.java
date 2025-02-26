package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Service interface for managing `Student` entities.
 * This interface provides methods for CRUD operations, filtering, sorting, and retrieving student data.
 * It ensures the proper handling of student records while maintaining data integrity and validation.
 * <p>
 * Key functionalities:
 * - Retrieve all students or filter and sort based on search criteria.
 * - Save multiple student records in bulk.
 * - Find students by their unique ID or example criteria.
 * - Create new student records or update existing ones.
 * - Delete students by their ID.
 * <p>
 * Validation:
 * - Enforces input validation using annotations like `@Valid` and `@NotEmpty`.
 * - Ensures compliance with domain-specific constraints for IDs and other parameters.
 * <p>
 * Dependencies:
 * - Annotated parameters provide consistent validation and error handling.
 * - Relies on custom validators for enhanced validation rules.
 *
 * @author Inessa Repeshko CS-222a
 * @see Student
 * @see ValidId
 * @see Valid
 * @see List
 */

@Validated
public interface StudentService {
    List<Student> getAllStudents();

    List<Student> getFilteredAndSortedStudents(String search,
                                               String fundingType,
                                               String scholarshipStatus,
                                               String sort);

    void saveAllStudents(@NotEmpty(message = "The student list must not be null.") List<@Valid Student> students);

    Student findStudentById(@ValidId Long id);

    Student findStudentByExample(@Valid Student student);

    Student saveStudent(@Valid Student student);

    Student updateStudent(@Valid Student student);

    void deleteStudent(@ValidId Long id);
}
